reset
set style fill transparent solid 0.4
set terminal png
set output 'Graph.png'
set multiplot
set title 'Graph'
set xrange[] writeback
set yrange[] writeback
set trange[] writeback

f0(x) = -1.67*x +0.00
f1(x) = -1.67*x +7.00
f2(x) = -1.67*x +8.33
f3(x) = -5.00*x +15.00
f4(x) = -1.00*x +7.00
set label 1 '     (2.00 ; 5.00)' at 2.00,5.00 point ps 2 pointtype 2

plot f0(x) lt rgb 'black' title '0,00 = 0.05X1 + 0.03X2',f1(x) lt rgb 'gold' title '0,21 = 0.05X1 + 0.03X2',f2(x) lt rgb 'dark-orange' title '0,25 = 0.05X1 + 0.03X2',f3(x) lt rgb 'grey' title '50X1 + 10X2 >= 150',f4(x) lt rgb 'green' title '30X1 + 30X2 >= 210'

set parametric
set xrange restore
set yrange restore
set trange [-500:500]


unset parametric
unset multiplot
unset output
unset terminal
