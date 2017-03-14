#!/usr/bin/perl
# Name : Caleb Miller
# Class : 2850-001
# Program # : 3
# Due Date : 1/26/2016
#
# Colleagues: NONE
# This program breaks a 5 digit number into its individual digits

use Modern::Perl;
print "Enter a five-digit number: ";
my $num = <>;
chomp $num;
my $string="";
print "ERROR: Your number must be five digits in length\n" if $num<10000;
print "ERROR: Your number must be five digits in length\n" if $num>=100000;
if ($num>=10000) {
    if ($num <100000) {
        $string = sprintf "$string%-3d", int($num/10000);
        $num %= 10000;
        $string = sprintf "$string%-3d", int($num/1000);
        $num%= 1000;
        $string = sprintf "$string%-3d", int($num/100);
        $num %= 100;
        $string = sprintf "$string%-3d", int($num/10);
        $num%= 10;
        $string = sprintf "$string%-3d", int($num);
        print"$string\n";
    }
}
