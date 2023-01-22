
-- regular queries
SELECT wins FROM soccer;
SELECT team_name, wins FROM soccer;
SELECT team_name, wins FROM soccer;
SELECT team_name, wins FROM soccer WHERE league = 'Premier League';
SELECT team_name, wins FROM soccer ORDER BY wins DESC;
SELECT team_name, wins FROM soccer WHERE wins > 20;
SELECT team_name, wins FROM soccer WHERE wins < 20;

-- regular text queries
SELECT team_name, wins FROM soccer WHERE team_name LIKE '%United%';
SELECT team_name, wins FROM soccer WHERE league = 'Premier League' AND wins > 20;
SELECT team_name FROM soccer WHERE team_name LIKE '%United%';

SELECT * FROM soccer 
  WHERE team_name LIKE ('%United%')
  OR team_name IN ('Bayern Munich', 'Chelsea', 'Real Madrid', 'Barcelona')
  ORDER BY league;
