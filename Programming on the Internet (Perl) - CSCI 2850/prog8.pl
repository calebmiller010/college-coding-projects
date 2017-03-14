#!/usr/bin/perl
# Name : Caleb Miller
# Class : 2850-001
# Program # : 8
# Due Date : 3/3/2016
#
# Colleagues: NONE
# This program uses REGEX to gather data and send it in an e-mail from a web 
# page listing out movies

use Modern::Perl;
use Mail::Sendmail;
my $length = @ARGV;
if ($length==1) {

    my $pageToGrab = "http://boxofficemojo.com/weekend/chart/";
    my $command = "/usr/bin/lynx -dump -width=205 $pageToGrab";
    my $CW, my $LW, my $name, my $weekend, my $total, my $bigdebut, my $weakdebut, my $gainmovie, my $lossmovie;
    my $allData = `$command`;
    my $gain = 0;
    my $loss = 0;
    my $send;
    my $mailTo = shift;
    my $mailFrom = 'calebmiller@loki.ist.unomaha.edu';
    my $subjectLine = "Weekend Box Office Report";

    $send.="Data scraped from $pageToGrab\n\n";
    $send=$send.sprintf("%-4s%-4s%-38s%-15s%-15s\n\n","CW", "LW", "Movie Title", "Weekend", " Total");

    while($allData=~/(\d+)\s+(\S+)\s+\[\d+\](.+?)\s+\S+\s+\$(\S+)(?:\s+\S+){4}\s+\$(\S+)\s+\S+\s+\S+\n/g) {

        ($CW, $LW, $name, $weekend, $total) = ($1,$2,$3,$4,$5);
        $send=$send.sprintf("%-4d%-4s%-38s\$%-15s\$%-15s\n",$CW, $LW, substr($name,0,35), $weekend, $total);

        if($2 =~ "N") {
            $weakdebut = $name." (".$CW.")";
            if(!$bigdebut) {
                $bigdebut .= $name." (".$CW.")";
            }
        }

        if(!($LW=~"-" || $LW=~"N")) {
            if ($CW-$LW>$loss){
                $loss=$CW-$LW;
                $lossmovie=$name." (".$loss." places) ";
            }
            elsif ($CW-$LW==$loss){
                $lossmovie.=$name." (".$loss." places) ";
            }
            if((-1*($CW-$LW))>$gain) {
                $gain=($CW-$LW)*-1;
                $gainmovie=$name." (".$gain." places) ";
            }
            elsif ($CW-$LW==$gain){ 
                $gainmovie.=$name." (".$gain." places) ";
            }
        }
    }

    $send.="\nBiggest debut: $bigdebut\n";
    $send.="Weakest debut: $weakdebut\n";
    if($gain==0) {
        $send.="Biggest Gain: NONE\n";
    }
    else {
        $send.="Biggest Gain: $gainmovie\n";
    }
    if($loss==0) {
        $send.="Biggest Loss: NONE\n";
    }
    else {
        $send.="Biggest Loss: $lossmovie\n";
    }
    $send = "<pre>".$send."</pre>";
    my %mail = ( To => $mailTo,
        From => $mailFrom,
        Subject => $subjectLine,
        Message => $send,
        'Content-Type' => 'text/html; charset="utf-8"'
        );
    if (sendmail %mail) {
        print "Successfully sent mail to $mailTo. Check your box!\n";
    }
    else {
        print "Error sending mail: $Mail::Sendmail::error \n";
    }
}
else {
    print ("Usage: $0 e-mail\n");
}
