#!/usr/bin/perl
# Name : Caleb Miller
# Class : 2850-001
# Program # : 4
# Due Date : 1/29/2016
#
# Colleagues: NONE
# This program determines whether the user input is a palindrome or not

use Modern::Perl;
print "Enter in a 9 character phrase: ";
my $word = <>;
chomp $word;
my $c9 = chop $word;
my $c8 = chop $word;
my $c7 = chop $word;
my $c6 = chop $word;
my $c5 = chop $word;
my $c4 = chop $word;
my $c3 = chop $word;
my $c2 = chop $word;
my $c1 = chop $word;
if($c9 ~~ $c1 && $c8 ~~ $c2 && $c7 ~~ $c3 && $c6 ~~ $c4)
{
    say "PALINDROME";
}
else
{
    say "NOT";
}
