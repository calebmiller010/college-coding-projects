#!/usr/bin/perl
# Name : Caleb Miller
# Class : 2850-001
# Program # : 6
# Due Date : 2/11/2016
#
# Colleagues: NONE
# This program uses arrays to make a simple ballot voting program
use Modern::Perl;
my @c1 =(
    'Best Picture', 
    'The Big Short', 
    'Bridge of Spies', 
    'Brooklyn', 
    'Mad Max: Fury Road', 
    'The Martian', 
    'The Revenant',
    'Room',
    'Spotlight',
);
my @c2 = (
    'Best Actor', 
    'Bryan Cranston, Trumbo',
    'Matt Damon, The Martian',
    'Leonardo DiCaprio, The Revenant',
    'Michael Fassbender, Steve Jobs',
    'Eddie Redmayne, The Danish Girl',
);
my @c3 = (
    'Best Actress',
    'Cate Blanchett, Carol',
    'Brie Larson, Room',
    'Jennifer Lawrence, Joy',
    'Charlotte Rampling, 45 Years',
    'Saoirse Ronan, Brooklyn',
);
my @c4 = (
    'Directing',
    'Adam McKay - The Big Short',
    'George Miller - Mad Max: Fury Road',
    'Alejandro G. Inarritu - The Revenant',
    'Lenny Abrahamson - Room',
    'Tom McCarthy - Spotlight',
);
my @c5 = (
    'Animated Feature Film',
    'Anomalisa',
    'Boy and the World',
    'Inside Out',
    'Shaun the Sheep Movie',
    'When Marnie Was There',
);
my @c6 = (
    'Makeup and Hair Styling',
    'Mad Max: Fury Road',
    'The Hundred-Year-Old Man Who Climbed Out the Window and Disappeared',
    'The Revenant',
);
my @c7 = (
    'Original Song',
    '“Earned It” - Fifty Shades of Grey',
    '“Manta Ray” - Racing Extinction',
    '“Simple Song #3” - Youth',
    '“Til It Happens to You” - The Hunting Ground',
    '“Writing’s on the Wall” - Spectre',
);
my @c8 = (
    'Adapted Screenplay',
    'The Big Short',
    'Brooklyn',
    'Carol',
    'The Martian',
    'Room',
);
my @choices = (\@c1,\@c2,\@c3,\@c4,\@c5,\@c6,\@c7,\@c8);
my @answers;
my $num = 0;
print "Welcome to the 88th Emmy Awards!\n";
for my $award ( @choices ) {
    print "\n" . "=" x 78;
    print "\nThe nominees for $$award[0] are: \n\n";
    $num = 1;
    for my $nominee ( @$award[1..$#$award] ) {
        print "\t[$num] $nominee\n";
        $num++;
    }
    print "\t[$num] Write In\n";
    print "\nPlease enter your choice for $$award[0]: ";
    chomp( my $user = <> );
    while( $user < 1 || $user > $#$award + 1 ) {
        print "I'm sorry, but $user is not a valid option";
        print "\nPlease enter your selection: ";
        chomp( $user = <> );
    }
    if ($user == $#$award+1) {
        print "Please enter your write-in candidate: ";
        chomp (my $manual = <> );
        push (@$award,$manual);
    }
    else {
        push(@$award,$$award[$user]);
    }
    print "Thank you for selecting $$award[$#$award] for the $$award[0] category!\n";
}

print "\n\nThank you for voting. Here is a summary of your votes: \n\n";
for my $award2 ( @choices) {
    my $answer = pop (@$award2);
    print "$$award2[0]:\n\t-$answer\n\n";
}
