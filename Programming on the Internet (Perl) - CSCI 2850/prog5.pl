#!/usr/bin/perl
# Name : Caleb Miller
# Class : 2850-001
# Program # : 5
# Due Date : 2/1/2016
#
# Colleagues: NONE
# This program converts a binary number to decimal
use Modern::Perl;
print "Please enter a binary number up to 30 digits: ";
my $b = <>;
chomp $b;
my $prnt = $b;
my @bin=0;
for(my $x=0; $x<30; $x++) {
    if($b) {
        $bin[$x]=chop $b;
    }
    else {
        $bin[$x]=0;
    }
}
my $sum = 0;
my $count = 0;
for my $num (@bin) {
    $sum+=($num*(2**$count));
    $count++;
}
print $prnt . " is $sum in decimal.\n";
