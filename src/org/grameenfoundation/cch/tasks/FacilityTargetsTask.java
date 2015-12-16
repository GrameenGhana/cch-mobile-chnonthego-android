package org.grameenfoundation.cch.tasks;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

public class FacilityTargetsTask extends AsyncTask<String, String, String> {
	private Context ctx;
	private DbHelper dbh;
	private double each_target;

	public FacilityTargetsTask(Context ctx) {
		this.ctx = ctx;
		this.dbh = new DbHelper(ctx);
	}

	@Override
	protected String doInBackground(String... urls) {
			
			  String response = "";
		         for (String url : urls) {
		             DefaultHttpClient client = new DefaultHttpClient();
		             HttpGet httpGet = new HttpGet(url);
		             try {
		                 HttpResponse execute = client.execute(httpGet);
		                 InputStream content = execute.getEntity().getContent();

		                 BufferedReader buffer = new BufferedReader(
		                         new InputStreamReader(content));
		                 String s = "";
		                 while ((s = buffer.readLine()) != null) {
		                     response += s;
		                 }

		             } catch (Exception e) {
		                 e.printStackTrace();
		             }
		         }
		         return response;

	}

	protected void onProgressUpdate(String... obj) {}
	
	@SuppressLint("NewApi")
	@Override
    protected void onPostExecute(String p) {
		dbh.deleteFacilityTargetsTable();
		JSONObject result;
		JSONObject target_group;
		JSONObject target_type;
		JSONArray target_name;
		try {
			result=new JSONObject(p);
			target_group=new JSONObject(result.getString("Child Health"));
			target_type=new JSONObject(target_group.getString("0-11 months"));
			JSONArray target_measles=new JSONArray(target_type.getString("Measles"));
				for(int i=0;i<target_measles.length();i++){
					System.out.println("Inserting Measles: "+ target_measles.getJSONObject(i).getString("month"));
				dbh.insertFacilityTarget("",
										"0-11 months", 
										"Measles", 
										target_measles.getJSONObject(i).getString("month"), 
										"Child Health", 
										target_measles.getJSONObject(i).getString("target"), 
										target_measles.getJSONObject(i).getString("actual"));
			}
				JSONArray target_measles_rubella=new JSONArray(target_type.getString("Measles Rubella"));
				for(int i=0;i<target_measles_rubella.length();i++){
					System.out.println("Inserting Measles Rubella: "+ target_measles.getJSONObject(i).getString("month"));
				dbh.insertFacilityTarget("",
										"0-11 months", 
										"Measles Rubella", 
										target_measles_rubella.getJSONObject(i).getString("month"), 
										"Child Health", 
										target_measles_rubella.getJSONObject(i).getString("target"), 
										target_measles_rubella.getJSONObject(i).getString("actual"));
				
				}
			
				JSONArray target_vitamin=new JSONArray(target_type.getString("Vitamin A"));
				for(int i=0;i<target_vitamin.length();i++){
					System.out.println("Inserting Vitamin A: "+ target_measles.getJSONObject(i).getString("month"));
				dbh.insertFacilityTarget("",
										"0-11 months", 
										"Vitamin A", 
										target_vitamin.getJSONObject(i).getString("month"), 
										"Child Health", 
										target_vitamin.getJSONObject(i).getString("target"), 
										target_vitamin.getJSONObject(i).getString("actual"));
				}
				JSONArray target_bcg=new JSONArray(target_type.getString("BCG"));
				for(int i=0;i<target_bcg.length();i++){
				dbh.insertFacilityTarget("",
										"0-11 months", 
										"BCG", 
										target_bcg.getJSONObject(i).getString("month"), 
										"Child Health", 
										target_bcg.getJSONObject(i).getString("target"), 
										target_bcg.getJSONObject(i).getString("actual"));
				}
				JSONArray target_opv0=new JSONArray(target_type.getString("OPV 0"));
				for(int i=0;i<target_opv0.length();i++){
				dbh.insertFacilityTarget("",
										"0-11 months", 
										"OPV 0", 
										target_opv0.getJSONObject(i).getString("month"), 
										"Child Health", 
										target_opv0.getJSONObject(i).getString("target"), 
										target_opv0.getJSONObject(i).getString("actual"));
				}
				JSONArray target_opv1=new JSONArray(target_type.getString("OPV 1"));
				for(int i=0;i<target_opv1.length();i++){
				dbh.insertFacilityTarget("",
										"0-11 months", 
										"OPV 1", 
										target_opv1.getJSONObject(i).getString("month"), 
										"Child Health", 
										target_opv1.getJSONObject(i).getString("target"), 
										target_opv1.getJSONObject(i).getString("actual"));
				}
				JSONArray target_opv2=new JSONArray(target_type.getString("OPV 2"));
				for(int i=0;i<target_opv2.length();i++){
				dbh.insertFacilityTarget("",
										"0-11 months", 
										"OPV 2", 
										target_opv2.getJSONObject(i).getString("month"), 
										"Child Health", 
										target_opv2.getJSONObject(i).getString("target"), 
										target_opv2.getJSONObject(i).getString("actual"));
				}
				JSONArray target_opv3=new JSONArray(target_type.getString("OPV 3"));
				for(int i=0;i<target_opv3.length();i++){
				dbh.insertFacilityTarget("",
										"0-11 months", 
										"OPV 3", 
										target_opv3.getJSONObject(i).getString("month"), 
										"Child Health", 
										target_opv3.getJSONObject(i).getString("target"), 
										target_opv3.getJSONObject(i).getString("actual"));
				}
				JSONArray target_penta1=new JSONArray(target_type.getString("Penta 1"));
				for(int i=0;i<target_penta1.length();i++){
				dbh.insertFacilityTarget("",
										"0-11 months", 
										"Penta 1", 
										target_penta1.getJSONObject(i).getString("month"), 
										"Child Health", 
										target_penta1.getJSONObject(i).getString("target"), 
										target_penta1.getJSONObject(i).getString("actual"));
				}
				JSONArray target_penta2=new JSONArray(target_type.getString("Penta 2"));
				for(int i=0;i<target_penta2.length();i++){
				dbh.insertFacilityTarget("",
										"0-11 months", 
										"Penta 2", 
										target_penta2.getJSONObject(i).getString("month"), 
										"Child Health", 
										target_penta2.getJSONObject(i).getString("target"), 
										target_penta2.getJSONObject(i).getString("actual"));
				}
				JSONArray target_penta3=new JSONArray(target_type.getString("Penta 3"));
				for(int i=0;i<target_penta3.length();i++){
				dbh.insertFacilityTarget("",
										"0-11 months", 
										"Penta 3", 
										target_penta3.getJSONObject(i).getString("month"), 
										"Child Health", 
										target_penta3.getJSONObject(i).getString("target"), 
										target_penta3.getJSONObject(i).getString("actual"));
				}
				JSONArray target_rota1=new JSONArray(target_type.getString("Rota 1"));
				for(int i=0;i<target_rota1.length();i++){
				dbh.insertFacilityTarget("",
										"0-11 months", 
										"Rota 1", 
										target_rota1.getJSONObject(i).getString("month"), 
										"Child Health", 
										target_rota1.getJSONObject(i).getString("target"), 
										target_rota1.getJSONObject(i).getString("actual"));
				}
				JSONArray target_rota2=new JSONArray(target_type.getString("Rota 2"));
				for(int i=0;i<target_rota2.length();i++){
				dbh.insertFacilityTarget("",
										"0-11 months", 
										"Rota 2", 
										target_rota2.getJSONObject(i).getString("month"), 
										"Child Health", 
										target_rota2.getJSONObject(i).getString("target"), 
										target_rota2.getJSONObject(i).getString("actual"));
				}
				JSONArray target_yellowfever=new JSONArray(target_type.getString("Yellow Fever"));
				for(int i=0;i<target_yellowfever.length();i++){
				dbh.insertFacilityTarget("",
										"0-11 months", 
										"Yellow Fever", 
										target_yellowfever.getJSONObject(i).getString("month"), 
										"Child Health", 
										target_yellowfever.getJSONObject(i).getString("target"), 
										target_yellowfever.getJSONObject(i).getString("actual"));
				}
				JSONObject target_type2=new JSONObject(target_group.getString("12-23 months"));
				
				JSONArray target_measles2=new JSONArray(target_type2.getString("Measles 2"));
				for(int i=0;i<target_measles2.length();i++){
				dbh.insertFacilityTarget("",
										"12-23 months", 
										"Measles 2", 
										target_measles2.getJSONObject(i).getString("month"), 
										"Child Health", 
										target_measles2.getJSONObject(i).getString("target"), 
										target_measles2.getJSONObject(i).getString("actual"));
			}
				JSONArray target_vitamina=new JSONArray(target_type2.getString("Vitamin A"));
				for(int i=0;i<target_vitamina.length();i++){
				dbh.insertFacilityTarget("",
										"12-23 months", 
										"Vitamin A", 
										target_vitamina.getJSONObject(i).getString("month"), 
										"Child Health", 
										target_vitamina.getJSONObject(i).getString("target"), 
										target_vitamina.getJSONObject(i).getString("actual"));
				}
				JSONObject target_type3=new JSONObject(target_group.getString("24-59 months"));
				JSONArray target_vitamina2=new JSONArray(target_type3.getString("Vitamin A"));
				for(int i=0;i<target_vitamina2.length();i++){
				dbh.insertFacilityTarget("",
										"24-59 months", 
										"Vitamin A", 
										target_vitamina2.getJSONObject(i).getString("month"), 
										"Child Health", 
										target_vitamina2.getJSONObject(i).getString("target"), 
										target_vitamina2.getJSONObject(i).getString("actual"));
			}
			
				
				
				JSONObject target_group2=new JSONObject(result.getString("Maternal Health"));
				JSONObject target_type4=new JSONObject(target_group2.getString("Expected Pregnancies"));
				
				JSONArray target_tt1=new JSONArray(target_type4.getString("TT1/TD1"));
					for(int i=0;i<target_tt1.length();i++){
					dbh.insertFacilityTarget("",
											"Expected Pregnancies", 
											"TT1/TD1", 
											target_tt1.getJSONObject(i).getString("month"), 
											"Maternal Health", 
											target_tt1.getJSONObject(i).getString("target"), 
											target_tt1.getJSONObject(i).getString("actual"));
				}
					JSONArray target_tt2=new JSONArray(target_type4.getString("TT2/TD2"));
					for(int i=0;i<target_tt2.length();i++){
					dbh.insertFacilityTarget("",
											"Expected Pregnancies", 
											"TT2/TD2", 
											target_tt2.getJSONObject(i).getString("month"), 
											"Maternal Health", 
											target_tt2.getJSONObject(i).getString("target"), 
											target_tt2.getJSONObject(i).getString("actual"));
				}
					JSONArray target_tt3=new JSONArray(target_type4.getString("TT3/TD3"));
					for(int i=0;i<target_tt3.length();i++){
					dbh.insertFacilityTarget("",
											"Expected Pregnancies", 
											"TT3/TD3", 
											target_tt3.getJSONObject(i).getString("month"), 
											"Maternal Health", 
											target_tt3.getJSONObject(i).getString("target"), 
											target_tt3.getJSONObject(i).getString("actual"));
				}
					JSONArray target_tt4=new JSONArray(target_type4.getString("TT4/TD4"));
					for(int i=0;i<target_tt4.length();i++){
					dbh.insertFacilityTarget("",
											"Expected Pregnancies", 
											"TT4/TD4", 
											target_tt4.getJSONObject(i).getString("month"), 
											"Maternal Health", 
											target_tt4.getJSONObject(i).getString("target"), 
											target_tt4.getJSONObject(i).getString("actual"));
				}
					JSONArray target_tt5=new JSONArray(target_type4.getString("TT5/TD5"));
					for(int i=0;i<target_tt5.length();i++){
					dbh.insertFacilityTarget("",
											"Expected Pregnancies", 
											"TT5/TD5", 
											target_tt5.getJSONObject(i).getString("month"), 
											"Maternal Health", 
											target_tt5.getJSONObject(i).getString("target"), 
											target_tt5.getJSONObject(i).getString("actual"));
				}
					JSONArray target_sp1=new JSONArray(target_type4.getString("SP1"));
					for(int i=0;i<target_sp1.length();i++){
					dbh.insertFacilityTarget("",
											"Expected Pregnancies", 
											"SP1", 
											target_sp1.getJSONObject(i).getString("month"), 
											"Maternal Health", 
											target_sp1.getJSONObject(i).getString("target"), 
											target_sp1.getJSONObject(i).getString("actual"));
				}
					JSONArray target_sp2=new JSONArray(target_type4.getString("SP2"));
					for(int i=0;i<target_sp2.length();i++){
					dbh.insertFacilityTarget("",
											"Expected Pregnancies", 
											"SP2", 
											target_sp2.getJSONObject(i).getString("month"), 
											"Maternal Health", 
											target_sp2.getJSONObject(i).getString("target"), 
											target_sp2.getJSONObject(i).getString("actual"));
				}
					JSONArray target_sp3=new JSONArray(target_type4.getString("SP3"));
					for(int i=0;i<target_sp3.length();i++){
					dbh.insertFacilityTarget("",
											"Expected Pregnancies", 
											"SP3", 
											target_sp3.getJSONObject(i).getString("month"), 
											"Maternal Health", 
											target_sp3.getJSONObject(i).getString("target"), 
											target_sp3.getJSONObject(i).getString("actual"));
				}
					JSONArray target_sp4=new JSONArray(target_type4.getString("SP4"));
					for(int i=0;i<target_sp4.length();i++){
					dbh.insertFacilityTarget("",
											"Expected Pregnancies", 
											"SP4", 
											target_sp4.getJSONObject(i).getString("month"), 
											"Maternal Health", 
											target_sp4.getJSONObject(i).getString("target"), 
											target_sp4.getJSONObject(i).getString("actual"));
				}
					JSONArray target_sp5=new JSONArray(target_type4.getString("SP5"));
					for(int i=0;i<target_sp5.length();i++){
					dbh.insertFacilityTarget("",
											"Expected Pregnancies", 
											"SP5", 
											target_sp5.getJSONObject(i).getString("month"), 
											"Maternal Health", 
											target_sp5.getJSONObject(i).getString("target"), 
											target_sp5.getJSONObject(i).getString("actual"));
				}
					JSONObject target_type5=new JSONObject(target_group2.getString("Family Planning"));
					JSONArray target_family=new JSONArray(target_type5.getString("Continuous Acceptors"));
						for(int i=0;i<target_family.length();i++){
						dbh.insertFacilityTarget("",
												"Family Planning", 
												"Continuous Acceptors", 
												target_family.getJSONObject(i).getString("month"), 
												"Maternal Health", 
												target_family.getJSONObject(i).getString("target"), 
												target_family.getJSONObject(i).getString("actual"));
					}
						JSONArray target_family2=new JSONArray(target_type5.getString("New Acceptors"));
						for(int i=0;i<target_family2.length();i++){
						dbh.insertFacilityTarget("",
												"Family Planning", 
												"New Acceptors", 
												target_family2.getJSONObject(i).getString("month"), 
												"Maternal Health", 
												target_family2.getJSONObject(i).getString("target"), 
												target_family2.getJSONObject(i).getString("actual"));
					}
						
						
						
						JSONObject target_group3=new JSONObject(result.getString("Others"));
						JSONObject target_other=new JSONObject(target_group3.getString("50-60"));
						JSONArray target_name_other=new JSONArray(target_other.getString("Any Care"));
							for(int i=0;i<target_name_other.length();i++){
							dbh.insertFacilityTarget("",
													"50-60", 
													"Any Care", 
													target_name_other.getJSONObject(i).getString("month"), 
													"Others", 
													target_name_other.getJSONObject(i).getString("target"), 
													target_name_other.getJSONObject(i).getString("actual"));
						}
						
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		DateTime dateTime=new DateTime();
		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MM-yyyy");
		String start;
		String end;
		JSONArray targets_array;
		JSONArray targets_detail_array;
		JSONObject targets_object=new JSONObject();
		JSONObject targets_details=new JSONObject();
		try {
			targets_array=new JSONArray(p);
			
		
		for(int i=0;i<12;i++){
			System.out.println("Inserting: "+i);
			for(int j=0;j<targets_array.length();j++){
					targets_object=targets_array.getJSONObject(j);	
					System.out.println("Inserting: "+targets_object.toString());
					targets_detail_array=new JSONArray(targets_object.getString("target_detail"));
				for(int k=0;k<targets_detail_array.length();k++){
					targets_details=targets_detail_array.getJSONObject(k);		
					System.out.println("Inserting: "+targets_details.toString());
					each_target=((double)(Math.ceil((Integer.parseInt(targets_object.getString("target_overall")))/12.0)));
					String target=String.format("%.0f", each_target);
					dateTime=dateTime.withMonthOfYear(i+1);
					start=dateTime.dayOfMonth().withMinimumValue().toString("dd-MM-yy");
					end=dateTime.dayOfMonth().withMaximumValue().toString("dd-MM-yy");
					System.out.println("Inserting Target Type: "+TargetTypeNames(targets_object.getString("target_type")));
					dbh.insertFacilityTarget(i+1,TargetTypeNames(targets_object.getString("target_type")),
											targets_object.getString("target_category") ,
						   					targets_detail_array.getJSONObject(k).getString("name"),
						   					targets_object.getString("target_group") ,
						   					Integer.valueOf(targets_object.getString("target_overall")),
						   					Integer.valueOf(target), 0, 0, 
						   					start, end,
						   					"Not set",
						   					MobileLearning.CCH_TARGET_STATUS_NEW, "Not yet",
		   					"",dateTime.toString("MMMM"));
			}
			}
		}
		} catch (JSONException e) {
			e.printStackTrace();
		}*/
    }


		public String TargetTypeNames(String target_type){
			String refined_type = null;
			
			switch(target_type){
			case "expected_pregnancies":
				refined_type="Expected Pregnancies";
				break;
			case "chn_6_to_11_mnths":
				refined_type="6-11 months";
				break;
			case "chn_0_to_11_mnths":
				refined_type="0-11 months";
				break;
				
			case "chn_12_to_23_mnths":
				refined_type="12-23 months";
				break;
				
			case "chn_0_to_23_mnths":
				refined_type="0-23 months";
				break;
				
			case "chn_24_to_59_mnths":
				refined_type="24-59 months";
				break;
				
			case "chn_less_than_5_yrs":
				refined_type="< 5 years";
				break;
				
			case "men_women_50_to_60_yrs":
				refined_type="50yrs-60yrs";
				break;
			case "wifa_15_49_yrs":
				refined_type="WIFA (15-49 yrs)";
				break;
			}
			
			return refined_type;
		}
}
