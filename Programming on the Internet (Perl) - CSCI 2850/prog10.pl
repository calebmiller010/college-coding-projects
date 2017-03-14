#!/usr/bin/perl
# Name : Caleb Miller
# Class : 2850-001
# Program # : 10
# Due Date : 3/29/2016
#
# Colleagues: NONE
# This program processed log files using hashes to organize and sort all of the data.
# then it prints them out to another file ($printout variable) in a clean, readable
# format.

use Modern::Perl;
use Net::hostent;
die ("\tUsage: $0 logfile1.log [logfile2.log logfile3.log....]\n") if @ARGV < 1;
# system("clear");

my $numfile = @ARGV;
my $count = 0;
my $byte = 0;
my %hostname = ();
my %domain = ();
my %date = ();
my %hour = ();
my %scode = ();
my %url = ();
my %file = ();
my %useragent = ();
my %browser = ();
my %refer = ();
my %refperdomain = ();
my %os = ();
my $printout = "calebmiller.summary";
my $totalbytes = 0;

foreach my $arg (@ARGV) { # main loop to process and sort each line within the log file
    open FILE, $arg;
    my @wholefile = <FILE>;
    foreach my $line (@wholefile) {
        if ($line=~/(\S+) - - \[(\d+\/\w+\/\d+):(\d+:\d+:\d+)\s+.+?\]\s+"(\w+)\s+(.*?)\s+(.*?)"\s+(\d+)\s+(\d+)\s+"(.*?)"\s+"(.*?)"/) {
            my ($IP, $DATE, $TIME, $METHOD, $URL, $HTTP, $SCODE, $BYTE, $REF, $UAGENT) = ($1,$2,$3,$4,$5,$6,$7,$8,$9,$10);
            $hostname {$IP} ++; # build hash for 'hostnames'

            # build hash for 'hours'
            if ($TIME =~ /(\d+)/) { 
                $hour{$1}++;
            }

            $date{$DATE}++; # build hash for 'date'
            $scode{$SCODE}++; # build hash for 'status codes'
            $url{$URL}++; # build hash for 'url'

            # build hash for 'file types'
            if ($URL =~ /\.(\S+)$/) {
                my $tag = $1;
                if ($tag eq "cgi") {
                    $file{"CGI Program (cgi)"}++;
                }
                elsif ($tag eq "jpg" || $tag eq "jpeg" || $tag eq "gif" || $tag eq "ico" || $tag eq "png") {
                    $file{"Image (jpg,jpeg,gif,ico,png)"}++;
                }
                elsif ($tag eq "css") {
                    $file{"Style Sheet (CSS)"}++;
                }
                elsif ($tag eq "htm" || $tag eq "html") {
                    $file{"Web Pages (htm,html)"}++;
                }
                else {
                    $file{"Other"}++;
                }
            }
            else {
                $file{"Other"}++;
            }

            # build hash for 'browsers'
            if ($UAGENT eq "-") {
                $useragent{"NO BROWSER ID"}++;
            }
            else {
                $useragent{$UAGENT}++;
            }

            # build hash for 'browser family'
            if ($UAGENT =~ /msie/i) {
                $browser{"MSIE"}++;
            }
            elsif ($UAGENT =~ /chrome/i) {
                $browser{"Chrome"}++;
            }
            elsif ($UAGENT =~ /firefox/i) {
                $browser{"Firefox"}++;
            }
            elsif ($UAGENT =~ /opera/i) {
                $browser{"Opera"}++;
            }
            elsif ($UAGENT =~ /safari/i) {
                $browser{"Safari"}++;
            }
            else {
                $browser{"Unknown"}++;
            }
            
            # build hash for 'referer's domain' and 'referrers'
            if ($REF eq "-") {
                $refer{"NO REFERER"}++;
                $refperdomain{"NONE"}++;
            }
            else {
                $refer{$REF}++;
                if($REF =~ /https?\:\/\/(?:www)?.*?(\w+\.\w+)(?:(?:\b$)|\/)/) {
                    $refperdomain{$1}++;
                }
                else {
                    $refperdomain{"NONE"}++;
                }
            }

            # build hash for 'operating systems'
            if ($UAGENT =~ /linux/i) {
                $os{"Linux"}++;
            }
            elsif ($UAGENT =~ /window/i) {
                $os{"Windows"}++;
            }
            elsif ($UAGENT =~ /mac/i) {
                $os{"Macintosh"}++;
            }
            else {
                $os{"Other"}++;
            }
            #

            $totalbytes += $BYTE; # accumulate bytes
            $count++; #accumulate total files

        }


    }
}

open (FILE, '>', $printout) or die "Can't write to $printout: error $!\n";
$|=1;
my $progress = 0;
my $keys = keys %hostname;
print "Processing file(s)...\n";

# build hash for 'domains'
foreach my $ip (keys %hostname) {

    # loading bar
    my $percent = int(100*($progress/$keys));
    print "\r[";
    print "="x int($percent/2);
    print " "x (50-int($percent/2));
    printf ("]  %d%% completed...", $percent);
    #

    my $name = $ip;
    my $savenum = $hostname{$ip};
    if ( my $newhost = gethost($ip) ) {
        #switch out the stored name in the hash
        delete $hostname{$ip};
        $name = $newhost -> name();
        $hostname{$name} = $savenum;
        #
    }
    if ($name =~ /(?:\S+\.)?(\S+\.\D+)/ ) {
        $domain{$1}+= $savenum;
    }
    else { 
        $domain{"DOTTED QUAD OR OTHER"} += $savenum; 
    }
    $progress++;
}

print FILE "Web Server Log Analyzer\n\nProcessed $count entries from $numfile files.\nProcessed the following logfiles:\n";
foreach my $logfile (@ARGV) {
    print FILE "$logfile\n";
}
print FILE "\n";

# send all the hashes to go print to the file!
printout($count,'HOSTNAMES',\%hostname);
printout($count,'DOMAIN',\%domain);
printout($count,'DATES',\%date);
printout($count,'HOURS',\%hour);
printout($count,'STATUS CODE',\%scode);
printout($count,'URL',\%url);
printout($count,'FILE TYPES',\%file);
printout($count,'BROWSERS',\%useragent);
printout($count,'BROWSERS FAMILIES',\%browser);
printout($count,'REFERRERS',\%refer);
printout($count,"REFERRERS' DOMAIN",\%refperdomain);
printout($count,"OPERATING SYSTEMS",\%os);

print FILE "Total bytes served   =   $totalbytes\n\n";
print "\r[";
print "="x50;
print "]  100% completed...\nDONE!\n";

# subroutine 'printout' : used to print out in sorted order all the elements of the hash, 
# along with the percentage that is required in the assignment
sub printout {
    my ($count,$category, $ref)=@_;
    print FILE "="x76;
    print FILE "\n".$category."\n";
    print FILE "="x76;
    print FILE "\n\n  Hits  %-age   Resource\n  ----  -----   --------\n";
    foreach my $resource (sort keys %$ref) {
        my $num = $$ref{$resource};
        printf FILE "%6d%7.2f   %-76s\n", $num, $num*100/$count, $resource ;
    }
    printf FILE " -----\n%6d entries displayed\n\n\n", $count;

}
