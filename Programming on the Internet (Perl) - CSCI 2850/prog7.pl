#!/usr/bin/perl
# Name : Caleb Miller
# Class : 2850-001
# Program # : 7
# Due Date : 2/23/2016
#
# Colleagues: NONE
# This program uses REGEX to gather information on UNIX's 'last' command
use Modern::Perl;
my $name = $ARGV[0];
my $length = @ARGV;
my ($hours, $lines, $days, $minutes, $m, $d, $h) = (0,0,0,0,0,0,0);
my @capture = `last $name`;
if ($length == 1) {
    print "Here is a listing of the logins for $name:";
    foreach my $line (@capture)
    {
        last if ( $line ~~ m/^\s/ ); #break loop once we hit the blank line
        print"\n\n" if $lines == 0;
        $lines++;
        printf "%3d. $line", $lines;
        ($d, $h, $m) = ($line =~ /\((\d?)\+?(\d+):(\d+)\)\s+$/); #REGEX to gather data from the time logged category
        if($h||$d||$m) { #make sure the REGEX found a valid match
            $days+=$d;
            $hours+=$h;
            $minutes+=$m;
        }
    }
    $hours+=int($minutes/60)+($days*24); #total hours
    $minutes%=60; #minutes
    print "\nHere is a summary of the time spent on the system for $name:\n\n";
    print "$name\n";
    print "$lines\n";
    printf "%02d:%02d\n\n", $hours, $minutes;
}
else {
    print "Usage: $0 login\n"; #print error message if CL argument was not entered correctly
}
