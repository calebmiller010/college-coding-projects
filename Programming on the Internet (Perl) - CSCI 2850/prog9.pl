#!/usr/bin/perl
# Name : Caleb Miller
# Class : 2850-001
# Program # : 9
# Due Date : 3/15/2016
#
# Colleagues: NONE
# This program is a modified version of the `finger` function that
# will find all occurences of similar sounding names/id's
use Modern::Perl;

die ("\tUsage: $0 possiblematch\n") if @ARGV != 1;
my $name = shift;
my $sdx = &soundex($name);
print "The name you were looking for, $name, converted to $sdx\n\n";
open (IN,"/etc/passwd");
my $found = 0;
my $userid;
my @args;
my @wholeFile=<IN>;
close IN;
for my $line (@wholeFile) {
    my ($id, $first, $last) = &getNames($line);
    if ( $sdx eq &soundex($id) || $sdx eq &soundex($first) || $sdx eq &soundex($last) ) 
    {
        @args = ( "finger", $id );
        system (@args);
        print"\n";
        $found++;
    }
}
system("finger",$name) if ($found==0);

sub getNames {
    my $input = shift;
    $input =~ /(\S*?):\S*?:\S*?:\S*?:(.*?)(?:,.*,.*,.*)?:.*/g; # this regex isolates the userid in $1 and their full name in $2
#   example
#   in 'rfulkerson:*:14608:140608:Robert A. Fulkerson,PKI 174B,N/A,N/A:/home/rfulkerson:/bin/bash'
#   $1 = rfulkerson and $2 = Robert A. Fulkerson
    my $userid = $1;
    $input = $2; # assign the full name to $input
    my @words = split(/\b(.+?)\s/,$input); # break up the full name into an array
    my $firstname = $words[1] || ""; # get the first name (index 0 is "" if the name has more than 1 word)
    my $lastname = $words[$#words]||""; # get the last name
    return ($userid, $firstname, $lastname);
}

sub soundex {
    my $input = uc shift; # make uppercase
    my $first = substr($input, 0, 1); # pull the first letter
    # replace one or more or these letters next to eachother with a number
    $input =~ s/[BFPV]+/1/g;
    $input =~ s/[CGJKQSXZ]+/2/g; 
    $input =~ s/[DT]+/3/g;
    $input =~ s/[L]+/4/g;
    $input =~ s/[MN]+/5/g;
    $input =~ s/[R]+/6/g;
    $input =~ s/[^123456BFPVCGJKQSXZDTLMNR]+/9/g; # replace any other letter with a 9
    $input = reverse($input);
    chop($input); # chop off the first number (we've already saved the first letter from the 2nd line of &soundex)
    $input = reverse($input);
    $input =~ s/9+//g; # remove any 9's from the string
    return $first.substr(substr( $input, 0, 4 )."0000",0,4); # format the string to truncate, add 0's on the end, and truncate again
}
