width=86.4-5;
height=91.4-5;
lcd_width=67.9;
lcd_height=63.9;
disp_wall_thickness = 1.0;
glass_width=width-2*disp_wall_thickness;
glass_height=67;




difference() {
    
    
    translate([-width/2+5,-height/2+5,0]) {

        minkowski(){
            cube([width-10,height-10,15],center=false);
            rotate([0,0,90]) cylinder(h=10,r=5);
        }
    }

    translate([-glass_width/2+5,-(glass_height - height/2)+5-disp_wall_thickness,16])
        minkowski(){
            cube([glass_width-10,glass_height-10,4.001],center=false);
            rotate([0,0,90]) cylinder(h=5,r=5);
        }
    
    translate([0,-height/2-12,31])
        rotate([30,0,0])
            cube([120,30,30],center=true);
        
    translate([-lcd_width/2,-(height-lcd_height)-2,10])
        cube([lcd_width,lcd_height,40],center=false);
        
    translate([-32,-(glass_height - height/2)+5-disp_wall_thickness,-1])
        minkowski(){
            cube([64,1,4.001],center=false);
            rotate([0,0,90]) cylinder(h=40,r=5);
        }
    translate([width/2-15, -height/2+10,10])
        cylinder(h=40,d=10,center=true);
    
    translate([width/2-15, -height/2+10,10])
        cube([18,18,20.001],center=true);    
}