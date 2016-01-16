reset
set style fill transparent solid 0.4
set terminal png
set output 'graph.png'
set multiplot
set title 'graph'
set xrange[] writeback
set yrange[] writeback
set trange[] writeback

f0(x) = -0.50*x +0.00
f1(x) = -0.50*x +2.00
f2(x) = -0.50*x +3.00
f4(x) = 0.50*x +2.00
set label 1 '     (1.00 ; 2.50)' at 1.00,2.50 point ps 2 pointtype 2

plot f0(x) lt rgb 'black' title '0,00 = x1 + 2x2',f1(x) lt rgb 'gold' title '4,00 = x1 + 2x2',f2(x) lt rgb 'dark-orange' title '6,00 = x1 + 2x2',NaN lt rgb 'grey' title 'x1 <= 1',f4(x) lt rgb 'red' title '-1x1 +2x2<= 4'

set parametric
set xrange restore
set yrange restore
set trange [-500:500]
plot 1.00,t lt rgb 'grey' notitle

unset parametric
unset multiplot
unset output
unset terminal
