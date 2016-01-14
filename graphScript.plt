reset
set multiplot

set style fill transparent solid 0.4
set title 'Grafico bonito'

# turn everything off
set format x ""   #numbers off
set format y ""
set xlabel ""     #label off
set ylabel ""
set border 0      #border off
unset xtics       #tics off
unset ytics
unset grid        #grid off
unset title       #title off

 r1(x) = 1.50 +1.50*x
 r2(x) = 3.00 -3.00*x
plot r1(x) with filledcurve x1 lt rgb 'black' title 'reta diagonal',NaN w l ls 2 lt 2 title "parametric line"

set parametric
const = 3
plot const,t with filledcurve y1 lt 5 notitle

#unset parametric
unset multiplot