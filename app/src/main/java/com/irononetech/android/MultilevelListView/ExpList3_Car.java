package com.irononetech.android.MultilevelListView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ExpandableListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.irononetech.android.Application.Application;
import com.irononetech.android.Webservice.URL;
import com.irononetech.android.claimsone.R;
import com.irononetech.android.formcomponent.FormObject;
import com.irononetech.android.formcomponent.view.VehicleDetailsActivity;

public class ExpList3_Car extends Activity implements
	SearchView.OnQueryTextListener, SearchView.OnCloseListener{
	Logger LOG = LoggerFactory.getLogger(ExpList3_Car.class);

	FormObject fo;

	static boolean booleanlistCar[][][][];
	static boolean booleanprelistCar[][][][];
	boolean booleanlistCarCopy[][][][];
	boolean booleanprelistCarCopy[][][][];
	int p1, p2, p3, p4;
	Button resetBtn;

	LinearLayout damageditems_header;

	EditText otherfield;

	private SearchView search;
	private MyListAdapter listAdapter;
	private ExpandableListView myList;
	private ArrayList<MainPart> mainPartsList = new ArrayList<MainPart>();

	private static final boolean [] CHECK_STATUS_TYRE  = {false, false, false, true, false, false, false, true, false, false, false, true, false, false, false, true, false, false, false, true, false, false, false, true};
	static final String freqDamagedItemMapper [] = {"5/0", "5/1" , "5/43", "5/39", "5/7", "5/11",
			"5/13", "5/15", "5/40", "5/8", "5/12", "5/14"
			, "5/41", "5/42", "5/32", "5/21", "5/38",
			"8/0", "8/4", "8/5", "8/6", "8/38", "8/42", "8/39", "8/9", "8/36",
			"8/40", "8/41",
			"6/0", "6/42", "6/43", "6/6", "6/17","6/49", "6/28",
			"6/44", "6/45", "6/46","6/47", "6/48",
			"7/0", "7/42", "7/43","7/6", "7/17", "7/49","7/28", "7/44",
			"7/45","7/46", "7/47", "7/48"};
 	static final String listdesc[][][][] = { { // grey
			{ // frequently damaged items
					//front
					{ "Cars / Double cabs / Jeeps", "Frequently damaged items" },
					{ "Front Buffer", "FRONT" }, { "Bonnet/engine hood", "FRONT" }, { "Front Shell", "FRONT" },
					{ "Windscreen *Heater *Sensor", "FRONT" }, {"R/H/S Head Lamp", "FRONT" }, {"R/H/S Front Signal Lamp", "FRONT" },
					{"R/H/S Front Parking Lamp", "FRONT"}
					,{"R/H/S Front Fog Lamp", "FRONT"},{"R/S Corner Panel", "FRONT"},{"L/H/S Head Lamp", "FRONT"},
					{"L/H/S Front Signal Lamp", "FRONT"},{"L/H/S Front Parking Lamp", "FRONT"},{"L/S Fog Lamp", "FRONT"},
					{"L/S Corner Panel", "FRONT"},{"Radiator", "FRONT"},{"A/C Condenser", "FRONT"},
					{"Front Body kits", "FRONT"},
					//rear
					{"Rear Buffer", "REAR"}, {"R/H/S Tail Lights", "REAR"}
					,{"L/H/S Tail Lights", "REAR"}, {"Rear dickey lid", "REAR"}, {"Dickey Garnish", "REAR"},
					{"Rear Windscreen", "REAR"},{"Buffer Inner Panel", "REAR"},{"Rear dickey spoilers", "REAR"},{"Rear Body Kits", "REAR"},
					{"Reverse Camera", "REAR"},{"Reverse sensor", "REAR"},
					//Right Side
					{"R/H/S Fender", "RIGHT"},
					{"R/S Over Fender", "RIGHT"},{"R/S Side Mirror*Camera Type", "RIGHT"}
					,{"R/H/S Front door", "RIGHT"},{"R/H/S Rear door", "RIGHT"},{"Right Rocker panel", "RIGHT"},
					{"R/H/S Quarter panel", "RIGHT"},{"F/R/S Wheel Rim", "RIGHT"},{"R/R/S Wheel Rim", "RIGHT"},
					{"F/R/S Tyre", "RIGHT"},{"R/R/S Tyre", "RIGHT"}
					,{"R/S Quarter Glass", "RIGHT"}
					//Left Side
					,{"L/H/S Fender", "LEFT"},{"L/S Over Fender", "LEFT"},{"L/S Side Mirror*Camera Type", "LEFT"}
					,{"L/H/S Front door", "LEFT"},{"L/H/S Rear door", "LEFT"},{"Left Rocker panel", "LEFT"},
					{"L/H/S Quarter panel", "LEFT"},{"F/L/S Wheel Rim", "LEFT"},{"R/L/S Wheel Rim", "LEFT"},
					{"F/L/S Tyre", "LEFT"},{"R/L/S Tyre", "LEFT"}
					,{"L/S Quarter Glass", "LEFT"}
			},
			{ // lightgray // 1
			{ "Cars / Double cabs / Jeeps", "Out side parts" },
					{ "Bonnet", "" }, { "Bumper", "" }, { "Cowl screen", "" },
					{ "Fascia rear", "" }, { "Shell", "" },
					{ "Pillar and hard trim", "" }, { "Rocker panel", "" } },
			{ // darkgray
			{ "Cars / Double cabs / Jeeps", "Doors" },
					{ "Front right door", "" }, { "Front left door", "" },
					{ "Rear right door", "" }, { "Rear left door", "" },
					{ "Door seal", "" }, { "Hinge", "" }, { "Lock", "" } },
			{ // darkgray
			{ "Cars / Double cabs / Jeeps", "Lights" }, { "Fog light", "" },
					{ "Head light", "" }, { "Interior light", "" },
					{ "Side lighting", "" }, { "Tail light", "" },
					{ "Signal light", "" }, { "Front light", "" } },
			{ // darkgray
			{ "Cars / Double cabs / Jeeps", "Engine components" },
					{ "Accessory belt", "" }, { "Dickey door", "" },
					{ "Air duct", "" }, { "Air intake housing", "" },
					{ "Air intake manifold", "" }, { "Crank case", "" },
					{ "Crank pulley", "" } },
			{ { "Cars / Double cabs / Jeeps", "Front face panel" },//5
					{ "Front Buffer", "" }, { "Bonnet/engine hood", "" },//1
					{ "Front windscreen", "" },
					{ "Front single windscreen beading", "" },//3
					{ "L/H/S front buffer end", "" },
					{ "R/H/S front buffer end", "" }, { "Front grill", "" },//6
					{ "R/H/S Head Lamp", "" }, { "L/H/S Head Lamp", "" },
					{ "R/H/S head lamp frame", "" },
					{ "L/H/S head lamp frame", "" },
					{ "R/H/S Front Signal Lamp", "" },//11
					{ "L/H/S Front Signal Lamp", "" },
					{ "R/H/S Front Parking Lamp", "" },
					{ "L/H/S Front Parking Lamp", "" },//14
					{ "R/H/S Front Fog Lamp", "" },
					{ "L/H/S front fog lamp", "" }, { "Front badge", "" },//17
					{ "Dash board", "" }, { "A/C blower", "" },
					{ "A/C Cooler", "" }, { "A/C Condenser", "" },//21
					{ "A/C Filter", "" }, { "Horn", "" },
					{ "Electric horn", "" }, { "Front number plate", "" },//25
					{ "Front number plate lamp", "" }, { "Trim packages", "" },
					{ "Spoilers", "" }, { "Crash bars", "" },//29
					{ "Antennas", "" }, { "Radiator panel", "" },
					{ "Radiator", "" }, { "R/H/S Front Wipers blades", "" },//33
					{ "L/H/S Front Wipers blades", "" },
					{ "Wiper motors", "" }, { "Sun Wiser", "" },//36
					{ "Nose cut panel", "" }, { "Front Body kits", "" }, {"Windscreen *Heater *Sensor", ""},
					{"R/S Corner Panel", ""}, {"L/S Fog Lamp", ""},//41
					{"L/S Corner Panel", ""}, {"Front Shell", "" }},//43
			{ { "Cars / Double cabs / Jeeps", "Right side panel" },//6
					{ "R/H/S Fender", "" }, { "R/H/S Fender lights", "" },
					{ "R/H/S Fender inner", "" },//2
					{ "R/H/S Mudguard arch", "" },
					{ "R/H/S Fender beadings", "" },//4
					{ "R/H/S Fender mirrors", "" }, { "R/H/S Front door", "" },
					{ "R/H/S Front door glass", "" },
					{ "R/H/S Front door beadings", "" },//8
					{ "R/H/S Front winders", "" },
					{ "R/H/S Front door locks", "" },
					{ "R/H/S Front door switches", "" },
					{ "R/H/S Outer door handle", "" },//12
					{ "R/H/S Inner door handle", "" },
					{ "R/H/S Front door upholstery", "" },
					{ "R/H/S Front door center beadings", "" },//15
					{ "R/H/S Front door motors", "" },
					{ "R/H/S Rear door", "" }, { "R/H/S Rear door glass", "" },//18
					{ "R/H/S Rear door beadings", "" },
					{ "R/H/S Rear door winders", "" },
					{ "R/H/S Rear door locks", "" },//21
					{ "R/H/S Rear door switches", "" },
					{ "R/H/S Outer door handle", "" },
					{ "R/H/S Inner door handle", "" },//24
					{ "R/H/S Rear door upholstery", "" },
					{ "R/H/S Rear door center beadings", "" },
					{ "R/H/S Rear door motors", "" },//27
					{ "R/H/S Quarter panel", "" },
					{ "R/H/S Quarter panel lights", "" },
					{ "R/H/S front door Hinges", "" },//30
					{ "R/H/S rear door Hinges", "" }, { "Tie rod", "" },
					{ "Rack end", "" }, { "Lower arm", "" },//34
					{ "Upper arm", "" }, { "Drag link", "" },
					{ "Ball joint", "" }, { "Sun Wiser", "" },//38
					{ "Right Body kits", "" }, { "Front Shock absorbers", "" },
					{ "Rear shock absorbers", "" } ,//41
					{"R/S Over Fender", ""},{"R/S Side Mirror*Camera Type", ""},{"F/R/S Wheel Rim", ""},//44
					{"R/R/S Wheel Rim", ""},
					{"F/R/S Tyre", ""},{"R/R/S Tyre", ""}//47
					,{"R/S Quarter Glass", ""}, { "Right Rocker panel", "" }},//49
			{ { "Cars / Double cabs / Jeeps", "Left side panel" },//7
					{ "L/H/S Fender", "" }, { "L/H/S Fender lights", "" },
					{ "L/H/S Fender inner", "" },//2
					{ "L/H/S Mudguard arch", "" },
					{ "L/H/S Fender beadings", "" },//4
					{ "L/H/S Fender mirrors", "" }, { "L/H/S Front door", "" },
					{ "L/H/S Front door glass", "" },//7
					{ "L/H/S Front door beadings", "" },
					{ "L/H/S Front winders", "" },
					{ "L/H/S Front door locks", "" },//10
					{ "L/H/S Outer door handle", "" },
					{ "L/H/S Inner door handle", "" },
					{ "L/H/S Front door switches", "" },
					{ "L/H/S Front door upholstery", "" },//14
					{ "L/H/S Front door center beadings", "" },
					{ "L/H/S Front door motors", "" },
					{ "L/H/S Rear door", "" }, { "L/H/S Rear door glass", "" },//18
					{ "L/H/S Rear door beadings", "" },
					{ "L/H/S Rear door winders", "" },
					{ "L/H/S Rear door locks", "" },
					{ "L/H/S Rear door switches", "" },//22
					{ "L/H/S Outer door handle", "" },
					{ "L/H/S Inner door handle", "" },
					{ "L/H/S Rear door upholstery", "" },
					{ "L/H/S Rear door center beadings", "" },//26
					{ "L/H/S Rear door motors", "" },
					{ "L/H/S Quarter panel", "" },
					{ "L/H/S Quarter panel lights", "" },
					{ "L/H/S front door Hinges", "" },//30
					{ "L/H/S rear door Hinges", "" }, { "Tie rod", "" },
					{ "Rack end", "" }, { "Lower arm", "" },//34
					{ "Upper arm", "" }, { "Drag link", "" },
					{ "Ball joint", "" }, { "Sun Wiser", "" },//38
					{ "Left Body kits", "" }, { "Front Shock absorbers", "" },
					{ "Rear shock absorbers", "" },{"L/S Over Fender", ""},{"L/S Side Mirror*Camera Type", ""}//43
					,{"F/L/S Wheel Rim", ""},{"R/L/S Wheel Rim", ""},
					{"F/L/S Tyre", ""},{"R/L/S Tyre", ""}//47
					,{"L/S Quarter Glass", ""}, {"Left Rocker panel", ""}},//49
			{ { "Cars / Double cabs / Jeeps", "Rear side" },//8
					{ "Rear Buffer", "" }, { "Rear Buffer lights", "" },//1
					{ "Rear Buffer sensors", "" },
					{ "Rear Buffer cameras", "" }, { "R/H/S Tail Lights", "" },//4
					{ "L/H/S Tail Lights", "" }, { "Rear dickey lid", "" },
					{ "Rear dickey lid lights", "" },//7
					{ "Rear dickey locks", "" },
					{ "Rear dickey spoilers", "" },//9
					{ "R/H/S signal lights", "" },//10
					{ "L/H/S signal lights", "" }, { "Rear Badges", "" },
					{ "Rear Garnishes", "" }, { "Hood spoilers", "" },//14
					{ "Hood racks", "" }, { "Rear spare wheel", "" },
					{ "Rear spare wheel holders", "" }, { "Rear glass", "" },//18
					{ "Rear glass beadings", "" }, { "Rear decks", "" },
					{ "Rear deck doors", "" },//21
					{ "Rear rolling over bars", "" },
					{ "Rear Canopy Rexene", "" }, { "Rear Trim packages", "" },
					{ "Rear door beadings", "" }, { "Rear flash lights", "" },//26
					{ "Carry boy assembly", "" },
					{ "Inner panel assembly", "" }, { "Battery (hybrid)", "" },//29
					{ "Outer door handle", "" }, { "Inner door handle", "" },
					{ "Wipers", "" }, { "Wiper motors", "" },//33
					{ "Sun Wiser", "" }, { "Rolling over bars", "" },
					{ "Rear Body Kits", "" }, { "Carry boy panel", "" },//37
					{"Dickey Garnish", ""}, {"Buffer Inner Panel", ""}
					, {"Reverse Camera", ""}, {"Reverse sensor", ""}, {"Rear Windscreen", ""}},//42
			{ { "Cars / Double cabs / Jeeps", "Engine compartment and components" },
					{ "Diesel, petrol engine (gasoline engine)", "" },
					{ "Accessory belt", "" }, { "Air duct", "" },
					{ "Air intake housing", "" },
					{ "Air intake manifold", "" }, { "Camshaft", "" },
					{ "Camshaft bearing", "" }, { "Camshaft fastener", "" },
					{ "Camshaft follower", "" },
					{ "Camshaft locking plate", "" },
					{ "Camshaft pushrod", "" }, { "Camshaft spacer ring", "" },
					{ "Connecting rod", "" }, { "Connecting rod bearing", "" },
					{ "Connecting rod bolt", "" },
					{ "Connecting rod washer", "" }, { "Crank case", "" },
					{ "Crank pulley", "" }, { "Crankshaft", "" },
					{ "Crankshaft oil seal", "" }, { "Cylinder head", "" },
					{ "Cylinder head cover", "" },
					{ "Other cylinder head cover parts", "" },
					{ "Cylinder head gasket", "" }, { "Distributor", "" },
					{ "Distributor cap", "" }, { "Drive belt", "" },
					{ "Engine block", "" }, { "Engine block parts", "" },
					{ "Engine cradle", "" },
					{ "Engine shake damper and vibration absorber", "" },
					{ "Engine valve", "" }, { "Fan belt", "" },
					{ "Gudgeon pin (wrist pin)", "" },
					{ "Harmonic balancer", "" }, { "Heater", "" },
					{ "Mounting bolt", "" },
					{ "Piston pin and crank pin", "" },
					{ "Piston pin bush", "" },
					{ "Piston ring and circlip", "" }, { "Piston valve", "" },
					{ "Poppet valve", "" },
					{ "PCV valve (positive crankcase ventilation valve)", "" },
					{ "Pulley part", "" }, { "Rocker arm", "" },
					{ "Rocker cover Starter motor", "" },
					{ "Rocker cover Starter pinion", "" },
					{ "Rocker cover Starter ring", "" },
					{ "Turbocharger and supercharger", "" }, { "Tappet", "" },
					{ "Timing tape", "" }, { "Valve cover", "" },
					{ "Valve housing", "" }, { "Valve spring", "" },
					{ "Valve stem seal", "" }, { "Water pump pulley", "" } },
			{ { "Cars / Double cabs / Jeeps", "Engine cooling system" },
					{ "Air blower", "" }, { "Coolant hose cooling fan", "" },
					{ "Fan blade", "" }, { "Fan clutch", "" },
					{ "Radiator bolt", "" }, { "Radiator shroud", "" },
					{ "Radiator gasket", "" }, { "Radiator pressure cap", "" },
					{ "Water neck", "" }, { "Water neck o-ring", "" },
					{ "Water pipe", "" }, { "Water pump", "" },
					{ "Water pump gasket", "" }, { "Water tank", "" },
					{ "Thermostat", "" } },
			{ { "Cars / Double cabs / Jeeps", "Engine oil system" },
					{ "Oil filter", "" }, { "Oil gasket", "" },
					{ "Oil pan", "" }, { "Oil pipe", "" }, { "Oil pump", "" },
					{ "Oil strainer", "" } },
			{ { "Cars / Double cabs / Jeeps", "Exhaust system", },
					{ "Catalytic converter", "" },
					{ "Exhaust clamp and bracket", "" },
					{ "Exhaust flange gasket", "" }, { "Exhaust gasket", "" },
					{ "Exhaust manifold", "" },
					{ "Exhaust manifold gasket", "" }, { "Exhaust pipe", "" },
					{ "Heat shield", "" }, { "Heat sleeving and tape", "" },
					{ "Muffler (silencer)", "" } },
			{ { "Cars / Double cabs / Jeeps", "Fuel supply system" },
					{ "Air filter", "" }, { "Carburetor", "" },
					{ "Carburetor parts", "" }, { "Choke cable", "" },
					{ "EGR valve", "" }, { "Fuel cap", "" },
					{ "Fuel cell", "" }, { "Fuel cell component", "" },
					{ "Fuel cooler", "" }, { "Fuel distributor", "" },
					{ "Fuel filter", "" }, { "Fuel filter seal", "" },
					{ "Fuel injector", "" }, { "Fuel injector nozzle", "" },
					{ "Fuel pump", "" }, { "Fuel pump gasket", "" },
					{ "Fuel pressure regulator", "" }, { "Fuel rail", "" },
					{ "Fuel tank", "" }, { "Fuel tank cover", "" },
					{ "Fuel water separator", "" }, { "Intake manifold", "" },
					{ "Intake manifold gasket", "" },
					{ "LPG (Liquefied petroleum gas) system assembly", "" },
					{ "Throttle body", "" }, { "Universal joint", "" } },
			{ { "Cars / Double cabs / Jeeps", "Suspension and steering systems" },
					{ "Beam axle", "" }, { "Control arm", "" },
					{ "Constant-velocity axle", "" },
					{ "Constant-velocity joint", "" }, { "Idler arm", "" },
					{ "Kingpin", "" }, { "Pan hard rod", "" },
					{ "Pitman arm", "" },
					{ "Power steering assembly and component", "" },
					{ "Rack end", "" }, { "Shock absorber", "" },
					{ "Spindle", "" }, { "Spring - Air spring", "" },
					{ "Spring - Coil spring", "" },
					{ "Spring - Leaf and parabolic leaf spring", "" },
					{ "Spring - Rubber spring", "" },
					{ "Spring - Spiral spring", "" },
					{ "Stabilizer bars and link", "" }, { "Steering arm", "" },
					{ "Steering box", "" }, { "Steering column assembly", "" },
					{ "Steering rack (A form of steering gear)", "" },
					{ "Steering shaft", "" },
					{ "Steering wheel (driving wheel)", "" }, { "Strut", "" },
					{ "Stub axle", "" }, { "Suspension link and bolt", "" },
					{ "Tie bar", "" }, { "Tie rod", "" },
					{ "Tie rod end", "" }, { "Trailing arm", "" } },
			{ { "Cars / Double cabs / Jeeps", "Transmission system" },
					{ "Adjustable pedal", "" },
					{ "Axle shaft", "" },
					{ "Bell housing", "" },
					{ "Timing belt", "" },
					{ "Cam belt", "" },
					{ "Other belts", "" },
					{ "Carrier assembly", "" },
					{ "Chain wheel and sprocket", "" },
					{ "Clutch assembly - Clutch cable", "" },
					{ "Clutch assembly - Clutch disk", "" },
					{ "Clutch assembly - Clutch fan", "" },
					{ "Clutch assembly - Clutch fork", "" },
					{ "Clutch assembly - Clutch hose", "" },
					{ "Clutch assembly - Clutch lever", "" },
					{ "Clutch assembly - Clutch lining", "" },
					{ "Clutch assembly - Clutch pedal", "" },
					{ "Clutch assembly - Clutch pressure plate", "" },
					{ "Clutch assembly - Clutch shoe", "" },
					{ "Clutch assembly - Clutch spring", "" },
					{ "Differential case - Pinion bearing", "" },
					{ "Differential case - Differential clutch", "" },
					{ "Differential case - Spider gears", "" },
					{ "Differential case - Differential casing", "" },
					{ "Differential flange", "" },
					{ "Differential gear", "" },
					{ "Differential seal", "" },
					{ "Flywheel - Flywheel ring gear", "" },
					{ "Gear coupling", "" },
					{ "Gear pump", "" },
					{ "Gear ring", "" },
					{ "Gear stick (gear stick, gear lever, selection lever, shift stick, gear shifter)", "" },
					{ "Gearbox", "" }, { "Idler gear", "" },
					{ "Knuckle", "" }, { "Master cylinder", "" },
					{ "Output shaft", "" }, { "Pinion", "" },
					{ "Planetary gear set", "" },
					{ "Prop shaft (drive shaft, propeller shaft)", "" },
					{ "Shift cable", "" }, { "Shift fork", "" },
					{ "Shift knob", "" }, { "Shift lever", "" },
					{ "Slave cylinder", "" }, { "Speed reducer", "" },
					{ "Speedometer gear", "" }, { "Steering gear", "" },
					{ "Torque converter", "" }, { "Transaxle housing", "" },
					{ "Transfer case", "" }, { "Transmission gear", "" },
					{ "Transmission pan", "" },
					{ "Transmission seal and bonded piston", "" },
					{ "Transmission spring", "" }, { "Transmission yolk", "" } },
			{ { "Cars / Double cabs / Jeeps", "Other miscellaneous parts" },
					{ "Adhesive tape and foil", "" }, { "Bolt cap", "" },
					{ "License plate bracket", "" },
					{ "Speedometer cable", "" }, { "Cotter pin", "" },
					{ "Decal", "" }, { "Dashboard", "" },
					{ "Center console", "" }, { "Glove compartment", "" },
					{ "Drag link", "" }, { "Dynamic seal", "" },
					{ "Fastener", "" },
					{ "Gasket: Flat, molded, profiled", "" },
					{ "Hood and trunk release cable", "" },
					{ "Horn and trumpet horn", "" },
					{ "Injection-molded parts", "" },
					{ "Instrument cluster", "" }, { "Label", "" },
					{ "Mirror", "" }, { "Mount and mounting", "" },
					{ "Name plate", "" }, { "Flange nut", "" },
					{ "Hex nut", "" }, { "O-ring", "" }, { "Paint", "" },
					{ "Rivet", "" }, { "Rubber (extruded and molded)", "" },
					{ "Screw", "" }, { "Shim", "" }, { "Sun visor", "" } }
 	} };

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		try {
			super.onCreate(icicle);
			LOG.debug("ENTRY onCreate");
			setContentView(R.layout.multilevel_expandable_list_main);

			SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
			search = (SearchView) findViewById(R.id.search_damaged_items);
			search.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
			search.setIconifiedByDefault(false);
			search.setOnQueryTextListener(this);
			search.setOnCloseListener(this);

			myList = (ExpandableListView) findViewById(R.id.expandableListView);

			fo = Application.getFormObjectInstance();
			otherfield = (EditText) findViewById(R.id.damagedItemsOtherField);

			resetBtn = (Button) findViewById(R.id.resetButton);
			damageditems_header = (LinearLayout) findViewById(R.id.damageditems_header);

			p1 = Application.get4DArrSizeSec1();
			p2 = Application.get4DArrSizeSec2();
			p3 = Application.get4DArrSizeSec3();
			p4 = Application.get4DArrSizeSec4();

			if (fo.getisPreSelected()){ // PRE
				otherfield.setText(fo.getpredamagedItemsOtherField());
				booleanprelistCar = Arrays.copyOf(fo.getBooleanprelistCar(), fo.getBooleanprelistCar().length);

				booleanprelistCarCopy = new boolean[p1][p2][p3][p4];
				for (int i = 0; i < listdesc.length; i++) {
					for (int j = 0; j < listdesc[i].length; j++) {
						ArrayList<SubPart> subParts = new ArrayList<SubPart>();
						for (int k = 0; k < listdesc[i][j].length -1; k++) {
							booleanprelistCarCopy[i][j][k][0] = booleanprelistCar[i][j][k][0];
							booleanprelistCarCopy[i][j][k][1] = booleanprelistCar[i][j][k][1];

							if(j==0){
								String[] indexValues = freqDamagedItemMapper[k].split("/");
								int x = Integer.parseInt(indexValues[0]); int y = Integer.parseInt(indexValues[1]);
								SubPart subPart = new SubPart(j+"/"+k, listdesc[i][j][k+1][0], booleanprelistCar[0][x-1][y][0], listdesc[i][j][k+1][1]);
								subParts.add(subPart);
							} else {
								SubPart subPart = new SubPart(j+"/"+k, listdesc[i][j][k+1][0],booleanprelistCar[i][j-1][k][0], listdesc[i][j][k+1][1]);
								subParts.add(subPart);
							}
						}
						MainPart mainPart = new MainPart(2+"/"+j+"0", listdesc[i][j][0][1],subParts);
						mainPartsList.add(mainPart);
					}
				}
				damageditems_header.setBackgroundDrawable(getResources().getDrawable(R.drawable.predamageditems_header));
			} else {
				otherfield.setText(fo.getdamagedItemsOtherField());
				booleanlistCar = Arrays.copyOf(fo.getBooleanlistCar(), fo.getBooleanlistCar().length);

				booleanlistCarCopy = new boolean[p1][p2][p3][p4];

				for (int i = 0; i < listdesc.length; i++) {
					for (int j = 0; j < listdesc[i].length; j++) {
						ArrayList<SubPart> subParts = new ArrayList<SubPart>();
						for (int k = 0; k < listdesc[i][j].length -1; k++) {
							booleanlistCarCopy[i][j][k][0] = booleanlistCar[i][j][k][0];
 							booleanlistCarCopy[i][j][k][1] = booleanlistCar[i][j][k][1];
 							if(j==0){
 								String[] indexValues = freqDamagedItemMapper[k].split("/");
 								int x = Integer.parseInt(indexValues[0]); int y = Integer.parseInt(indexValues[1]);
 								SubPart subPart = new SubPart(j+"/"+k, listdesc[i][j][k+1][0], booleanlistCar[0][x-1][y][0], listdesc[i][j][k+1][1]);
								subParts.add(subPart);
							} else {
								SubPart subPart = new SubPart(j+"/"+k, listdesc[i][j][k+1][0],booleanlistCar[i][j-1][k][0], listdesc[i][j][k+1][1]);
								subParts.add(subPart);
							}
						}

						MainPart mainPart = new MainPart(2+"/"+j, listdesc[i][j][0][1],subParts);
						mainPartsList.add(mainPart);
					}
				}

				damageditems_header.setBackgroundDrawable(getResources().getDrawable(R.drawable.damageditems_header));
			}

			if (!fo.isEditable()) {
				editDisable();
			}

			//display the list
			displayList();

			myList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
				@Override
				public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
					try {
						FormObject formObject = Application.getFormObjectInstance();
						if (!formObject.isEditable()) {

						} else {
							CheckBox checkBox = (CheckBox) v.findViewById(R.id.checkBox1);

							if(formObject.getisPreSelected()) {
								if (checkBox.isChecked()) { //if is already checked
									String text = listAdapter.getChildName(groupPosition, childPosition);
									for(int i =1; i<listdesc[0].length; i++){
										for(int j = 1; j<listdesc[0][i].length; j++){
											String text2 = listdesc[0][i][j][0];
											if (text.equals(text2)){
												if(i != 0 && j!=0) {ExpList3_Car.booleanprelistCar[0][i-1][j-1][0] = false;}
											}
										}
									}
									listAdapter.checkForDoubleItems(text, false);
								} else { //if user want to mark it damaged
									String text = listAdapter.getChildName(groupPosition, childPosition);
									for(int i =1; i<listdesc[0].length; i++){
										for(int j = 1; j<listdesc[0][i].length; j++){
											String text2 = listdesc[0][i][j][0];
											if (text.equals(text2)){
												if(i != 0 && j!=0) {ExpList3_Car.booleanprelistCar[0][i-1][j-1][0] = true;}
											}
										}
									}
									listAdapter.checkForDoubleItems(text, true);
								}
							} else { //if it is not pre selected
								if (checkBox.isChecked()) { //if is already checked
									String text = listAdapter.getChildName(groupPosition, childPosition);
									for(int i =1; i<listdesc[0].length; i++){
										for(int j = 1; j<listdesc[0][i].length; j++){
											String text2 = listdesc[0][i][j][0];
											if (text.equals(text2)){
												if(i != 0 && j!=0) {ExpList3_Car.booleanlistCar[0][i-1][j-1][0] = false;}
											}
										}
									}
									listAdapter.checkForDoubleItems(text, false);
								} else { //if user want to mark it damaged
									String text = listAdapter.getChildName(groupPosition, childPosition);
									for(int i =1; i<listdesc[0].length; i++){
										for(int j = 1; j<listdesc[0][i].length; j++){
											String text2 = listdesc[0][i][j][0];
											if (text.equals(text2)){
												if(i != 0 && j!=0) {ExpList3_Car.booleanlistCar[0][i-1][j-1][0] = true;}
											}
										}
									}
									listAdapter.checkForDoubleItems(text, true);
								}
							}
						}
					} catch (Exception e) {
            			LOG.error("onChildClick:10218");
						if(e != null){
                			LOG.error("Message: " + e.getMessage());
                			LOG.error("StackTrace: " + e.getStackTrace());
						}
					}
					return false;
				}
			});

			LOG.debug("SUCCESS onCreate");
		} catch (Exception e) {
			LOG.error("onCreate:10304");
			if (e != null) {
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}

	public void editDisable() {
		try {
			otherfield.setFocusable(false);
			resetBtn.setVisibility(View.GONE);
			/*
			 * if (fo.getisPreSelected()) // PRE {
			 * back.setBackgroundDrawable(getResources().getDrawable(
			 * R.drawable.back_button_xml)); } else {
			 * back.setBackgroundDrawable(getResources().getDrawable(
			 * R.drawable.back_button_xml)); }
			 */
		} catch (Exception e) {
			LOG.error("editDisable:10306");
			if (e != null) {
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}

	/*
	 * public void editEnable() { try { otherfield.setFocusable(true);
	 * 
	 * if (fo.getisPreSelected()) // PRE {
	 * back.setBackgroundDrawable(getResources().getDrawable(
	 * R.drawable.back_button_xml)); } else {
	 * back.setBackgroundDrawable(getResources().getDrawable(
	 * R.drawable.cancel_button_xml)); } } catch (NotFoundException e) {
	 * LOG.error("editEnable:10307"); if (e != null) { LOG.error("Message: " +
	 * e.getMessage()); LOG.error("StackTrace: " + e.getStackTrace()); } } catch
	 * (Exception e) { LOG.error("editEnable:10308"); if (e != null) {
	 * LOG.error("Message: " + e.getMessage()); LOG.error("StackTrace: " +
	 * e.getStackTrace()); } } }
	 */

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void multilevel_ok_click(View v) {
		okCommon();
	}

	private void okCommon() {
		try {
			if (fo.getisPreSelected()) // PRE
			{
				fo.setBooleanprelistCar(booleanprelistCar);
				fo.setpredamagedItemsOtherField(otherfield.getText().toString().trim());
			} else {
				fo.setBooleanlistCar(booleanlistCar);
				fo.setdamagedItemsOtherField(otherfield.getText().toString().trim());
			}
			Application.setFormObjectInstance(fo);
			
			Intent mIntent = new Intent(ExpList3_Car.this, VehicleDetailsActivity.class);
			mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(mIntent);
			finish();
		} catch (Exception e) {
			LOG.error("okCommon:10301");
			if (e != null) {
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}

	private void backCommon() {
		try {
			if (fo.getisPreSelected()) // PRE
			{
				fo.setpredamagedItemsOtherField(fo.getpredamagedItemsOtherField());
				fo.setBooleanprelistCar(booleanprelistCarCopy);
			} else {
				fo.setdamagedItemsOtherField(fo.getdamagedItemsOtherField());
				fo.setBooleanlistCar(booleanlistCarCopy);
			}
			Application.setFormObjectInstance(fo);
		} catch (Exception e) {
			LOG.error("backCommon:13388");
			if (e != null) {
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}

	public void multilevel_back_button(View v) {
		try {
			if (fo.isEditable()) {

				boolean isSameList = true;
				boolean isSameText = false;

				if (fo.getisPreSelected()) // PRE
				{
					// isSameList = Arrays.equals(booleanprelistBus,
					// booleanprelistBusCopy);
					for (int i = 0; i < p1; i++) {
						for (int j = 0; j < p2; j++) {
							for (int k = 0; k < p3; k++) {
								if (booleanprelistCarCopy[i][j][k][0] != booleanprelistCar[i][j][k][0]) {
									isSameList = false;
									break;
								}
								if (booleanprelistCarCopy[i][j][k][1] != booleanprelistCar[i][j][k][1]) {
									isSameList = false;
									break;
								}
							}
						}
					}

					if (otherfield.getText().toString().toLowerCase().equals((fo.getpredamagedItemsOtherField().toLowerCase())))
						isSameText = true;
				} else {
					// isSameList = Arrays.equals(booleanlistBus,
					// booleanlistBusCopy);
					for (int i = 0; i < p1; i++) {
						for (int j = 0; j < p2; j++) {
							for (int k = 0; k < p3; k++) {
								if (booleanlistCarCopy[i][j][k][0] != booleanlistCar[i][j][k][0]) {
									isSameList = false;
									break;
								}
								if (booleanlistCarCopy[i][j][k][1] != booleanlistCar[i][j][k][1]) {
									isSameList = false;
									break;
								}
							}
						}
					}

					if (otherfield.getText().toString().toLowerCase().equals((fo.getdamagedItemsOtherField().toLowerCase())))
						isSameText = true;
				}

				if (isSameList && isSameText) {
					finish();
					return;
				} else {
					AlertDialog.Builder alertbox = new AlertDialog.Builder(ExpList3_Car.this);
					alertbox.setTitle(R.string.saform);
					alertbox.setCancelable(false);
					alertbox.setMessage("Do you want to save changes?");
					alertbox.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface arg0, int arg1) {
									okCommon();
									removeDialog(arg1);
									finish();
								}
							});
					alertbox.setNegativeButton("No",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface arg0, int arg1) {
									backCommon();
									removeDialog(arg1);
									finish();
								}
							});
					alertbox.show();
					return;
				}

				/*
				 * boolean[][][][] a = new boolean[p1][p2][p3][p4]; if
				 * (!fo.getisPreSelected()) { // Damaged Items
				 * fo.setisVehicleShow(true); fo.setdamagedItemsOtherField("");
				 * fo.setpredamagedItemsOtherField(""); fo.setBooleanlistCar(a);
				 * fo.setBooleanprelistCar(a); } else { // Pre Damaged Items
				 * fo.setBooleanprelistCar(a);
				 * fo.setpredamagedItemsOtherField(otherfield.getText()
				 * .toString().trim()); }
				 */
			}
			finish();
		} catch (Exception e) {
			LOG.error("multilevel_back_button:10310");
			if (e != null) {
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}


	public void on_click_reset_button(View v) {
		try {
			AlertDialog.Builder alertbox = new AlertDialog.Builder(
					ExpList3_Car.this);
			alertbox.setTitle(R.string.saform);
			alertbox.setCancelable(false);
			alertbox.setMessage("'Point of Impact', 'Damaged Items', 'Tyre Conditions' and 'PAD Items' will be reset. Are you sure?");
			alertbox.setPositiveButton("Yes",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {
							fo.setisVehicleShow(true);
							fo.setdamagedItemsOtherField("");
							fo.setpredamagedItemsOtherField("");

							fo.setBooleanlistBus(new boolean[p1][p2][p3][p4]);
							fo.setBooleanlistCar(new boolean[p1][p2][p3][p4]);
							fo.setBooleanlistLorry(new boolean[p1][p2][p3][p4]);
							fo.setBooleanlistVan(new boolean[p1][p2][p3][p4]);
							fo.setBooleanlistThreeWheel(new boolean[p1][p2][p3][p4]);
							fo.setBooleanlistMotorcycle(new boolean[p1][p2][p3][p4]);
							fo.setBooleanlistTractor4WD(new boolean[p1][p2][p3][p4]);
							fo.setBooleanlistHandTractor(new boolean[p1][p2][p3][p4]);

							fo.setBooleanprelistBus(new boolean[p1][p2][p3][p4]);
							fo.setBooleanprelistCar(new boolean[p1][p2][p3][p4]);
							fo.setBooleanprelistLorry(new boolean[p1][p2][p3][p4]);
							fo.setBooleanprelistVan(new boolean[p1][p2][p3][p4]);
							fo.setBooleanprelistThreeWheel(new boolean[p1][p2][p3][p4]);
							fo.setBooleanprelistMotorcycle(new boolean[p1][p2][p3][p4]);
							fo.setBooleanprelistTractor4WD(new boolean[p1][p2][p3][p4]);
							fo.setBooleanprelistHandTractor(new boolean[p1][p2][p3][p4]);
							File imgFile = new  File(URL.getSLIC_JOBS() + fo.getJobNo() + "/PointsOfImpact/"
									+ fo.getJobNo() + "_PointsOfImpact.jpg");
							if(imgFile.exists()){
								imgFile.delete();
							}
							fo.setPointOfImpactList(null);
							fo.setselectTirecondition(CHECK_STATUS_TYRE);
                            fo.setvehicleType(0);

							Application.setFormObjectInstance(fo);
							removeDialog(arg1);
							finish();
						}
					});
			alertbox.setNegativeButton("No",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {
							removeDialog(arg1);
						}
					});
			alertbox.show();
		} catch (Exception e) {
			LOG.error("on_click_reset_button:10772");
			if (e != null) {
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}

	//method to expand all groups
	private void expandAll() {
		int count = listAdapter.getGroupCount();
		for (int i = 0; i < count; i++){
			myList.expandGroup(i);
		}
	}

	//method to collapse all group
	private void collapseAll(){
		int count = listAdapter.getGroupCount();
		for(int i = 0; i < count; i++){
			myList.collapseGroup(i);
		}
	}

	//method to expand all groups
	private void displayList() {
		//get reference to the ExpandableListView
		//create the adapter by passing your ArrayList data
		listAdapter = new MyListAdapter(ExpList3_Car.this, mainPartsList);
		//attach the adapter to the list
		myList.setAdapter(listAdapter);
	}

	@Override
	public boolean onClose() {
		listAdapter.filterData("");
		collapseAll();
		return false;
	}

	@Override
	public boolean onQueryTextChange(String query) {
		if(!query.equals("")){
			listAdapter.filterData(query);
			expandAll();
		}else{
			listAdapter.filterData(query);
			collapseAll();
		}

		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		listAdapter.filterData(query);
		expandAll();
		return false;
	}
}