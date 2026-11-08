# Assignment 4. Patient Zero

Student ID: 251567
Name: Shakhriyar Nurlanov
Group: SE-2512

This file is the report. Fill it in as you go. How to start: `START_HERE.md`. The task: `docs/Assignment4.pdf`.

## Program output

The 9 lines your program prints on your data (`java -cp out Main`):

    Mutations: 69
    Shared core: 869
    Marker hits: 5
    First hit: 11220
    Border length: 3
    Shared words: 372
    Tests needed: 29
    Cheapest testing: 133
    Longest chain: 26

My outbreak report (a screenshot of `output/outbreak.html`):

![outbreak](output/outbreak.png)

## Questions

Answer in your own words, two or three sentences each.

Mission 1. What does the cell d[i][j] of your table mean, and how is it computed from other cells?
#### The cell d[i][j] represents the minimum number of operations needed to transform the first i characters of string A into the first j characters of string B. The first row and column are initialized with the number of insertions or deletions. If the current characters are equal, we take the diagonal value. Otherwise, we take the minimum of replacement, deletion, and insertion, adding 1.
Mission 2. How is the longest common subsequence different from the longest common substring? My Shared core is bigger than the number of letters that are the same at the same place: why?
#### A subsequence keeps the same order of characters but does not require them to be next to each other. A substring must contain consecutive characters. If two characters are equal, we add 1 to the diagonal cell. Otherwise, we take the maximum of the upper and left cells. Shared Core can be bigger than the number of characters at the same positions because characters can match at different positions.
Mission 3. What does the prefix function tell you, and why does the pointer in the text never go back in KMP? What is the Border length of my marker and what does it mean?
#### The prefix function stores the length of the longest proper prefix that is also a suffix for each prefix of the pattern. KMP uses this information after a mismatch, so the text pointer never needs to move backwards. The Border length of my marker is 3, which means its longest proper prefix that is also a suffix has three characters.
Mission 4. Why must you compare the letters after the hashes are equal? What happens to the cost of Rabin-Karp if many windows have the same hash?
#### Hash values help us quickly find possible matching windows. However, different strings can have the same hash because of collisions, so we must compare their actual characters before counting a match. If many windows have the same hash, more comparisons are required and the algorithm can become slower.
Mission 5. What are the two states of a person in your DP, and how do you get the answer for a person from its children? Why does a greedy "test the person with most links first" not always give the cheapest answer?
#### For every person, I calculate two states: the minimum cost when this person is tested and the minimum cost when this person is not tested. If a person is not tested, all their children must be tested. If the person is tested, each child can be either tested or not tested, depending on which option is cheaper. A greedy algorithm does not always work because testing the person with the most connections may cost more than testing several cheaper people.
## AI use
#### I used ChatGPT to understand the assignment requirements, explain the algorithms
## Timing (Mission 7)

Milliseconds, average of 3 runs, from `java -cp out Bench`.

| test | size | time              |
|---|---|-------------------|
| naive search | 20 000 / 40 000 / 80 000 | 56,9/157,6/324,8  |
| KMP | 20 000 / 40 000 / 80 000 | 2,8/1,2/0,4       |
| edit distance | 500 / 1000 / 2000 / 4000 | 5,7/9,2/20,1/76,4 |

What do the numbers say about how the time grows? When you double the size, by how much does each time grow? Two or three sentences.
#### The naive search becomes slower when the text size increases because it compares the pattern again at every possible position. KMP has linear time complexity O(n + m), so its execution time grows approximately linearly with the text size. Edit distance uses a two-dimensional DP table, so doubling both string lengths increases the number of operations approximately four times.
## Complexity

n is the length of the text (or of a strain), m the length of the pattern, k the length of the window, v the number of people. Fill in every algorithm you wrote.

| algorithm | time | space | assumptions |
|---|-----|---|---|
| Edit distance | O(n × m)    | O(n × m)| Full DP table|
| LCS |   O(n × m)  | O(n × m)|Full DP table |
| Prefix function and KMP |  O(n + m)   |O(m + hits) |Linear search |
| Rabin-Karp (shared words) |  Expected O(n + m), worst O(n × m × k)   |O(m) | Hash collisions may require character comparisons|
| Tree DP |   O(v)  | O(v)|Tree with v people |

## Before you submit

- The four files in `data` and the screenshot `output/outbreak.png` are in the repository.
- `java tools/Check.java` shows all examples right.
- This README is filled in, the program output and the section AI use included.
- The work is pushed to main (at least one commit per mission) and the repository is public.
- The link is attached in Moodle.
