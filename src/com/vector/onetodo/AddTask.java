package com.vector.onetodo;

import it.feio.android.checklistview.ChecklistManager;
import it.feio.android.checklistview.exceptions.ViewNotSupportedException;
import it.feio.android.checklistview.interfaces.CheckListChangedListener;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.GridLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.androidquery.AQuery;
import com.mobeta.android.dslv.DragSortController;
import com.mobeta.android.dslv.DragSortListView;
import com.vector.onetodo.db.gen.AssignDao;
import com.vector.onetodo.db.gen.CheckList;
import com.vector.onetodo.db.gen.CheckListDao;
import com.vector.onetodo.db.gen.Comment;
import com.vector.onetodo.db.gen.CommentDao;
import com.vector.onetodo.db.gen.Friends;
import com.vector.onetodo.db.gen.FriendsDao;
import com.vector.onetodo.db.gen.Label;
import com.vector.onetodo.db.gen.LabelDao;
import com.vector.onetodo.db.gen.Reminder;
import com.vector.onetodo.db.gen.ReminderDao;
import com.vector.onetodo.db.gen.Repeat;
import com.vector.onetodo.db.gen.RepeatDao;
import com.vector.onetodo.db.gen.ShareDao;
import com.vector.onetodo.db.gen.ToDo;
import com.vector.onetodo.db.gen.ToDoDao;
import com.vector.onetodo.utils.Constants;
import com.vector.onetodo.utils.TypeFaces;
import com.vector.onetodo.utils.Utils;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

public class AddTask extends FragmentActivity {

	SharedPreferences comment_pref;

	public static View dialoglayout5;
	public static AQuery aq, aq_menu;
	static Button btn;
	int dayPosition;
	static Long f2 = null;
	ToDoDao tododao;
	AssignDao assigndao;
	CheckListDao checklistdao;
	FriendsDao friendsdao;
	LabelDao labeldao;
	ReminderDao reminderdao;
	RepeatDao repeatdao;
	ShareDao sharedao;
	CommentDao commentdao;
	List<ToDo> tod;
	ProgressDialog dialog;
	public static PopupWindow popupWindowAdd;
	private PopupWindow popupWindowTask, popupWindowEvent, popupWindowSchedule;
	DragSortListView listViewTask, listViewSchedule, listViewEvent;
	ArrayAdapter<String> adapterTask, adapterEvent, adapterSchedule;
	SQLiteDatabase db;
	public static FrameLayout layout_MainMenu;
	String[] array = { "Assign", "Due date", "Location", "Reminder", "Repeat",
			"Label", "Subtasks", "Notes", "Attachment" };
	public static String assignedSelectedID = "";
	protected static String assignedSelectedName = "";

	// *******************ADD DATA
	String title = null, notes = null, label_name = null, r_location = null,
			locationtype = "None", start_date = null, end_date = null,
			checklist_data = null, location = null, before = null,
			repeat = null, givenDateString = null, location_tag = "",
			repeatdate = null;
	int MaxId, titlecheck = -1, is_locationtype = -1, Position = 0;
	public static List<String> comment, commenttime;
	static SharedPreferences pref, label, attach;
	Boolean is_time = true, is_location = false, is_allday = false,
			is_alertEmail = false, is_alertNotification = false,
			is_priority = false, repeat_forever = false;
	long r_time = 0, r_repeat = 0, todo_id = 0, dateInMilliseconds = 0;

	static int priority = 0;
	HttpClient client;
	HttpPost post;
	List<NameValuePair> pairs;
	HttpResponse response = null;
	add asyn;

	SimpleDateFormat sdf;
	Date mDate = null;

	private DragSortListView.DropListener onDropTask = new DragSortListView.DropListener() {
		@Override
		public void drop(int from, int to) {
			if (from != to) {
				String item = adapterTask.getItem(from);
				adapterTask.remove(item);
				adapterTask.insert(item, to);
				inflateLayoutsTasks();
			}
		}
	};

	private DragSortListView.DropListener onDropEvent = new DragSortListView.DropListener() {
		@Override
		public void drop(int from, int to) {
			if (from != to) {
				String item = adapterEvent.getItem(from);
				adapterEvent.remove(item);
				adapterEvent.insert(item, to);
				inflateLayoutsEvents();
			}
		}
	};

	private DragSortListView.DropListener onDropSchedule = new DragSortListView.DropListener() {
		@Override
		public void drop(int from, int to) {
			if (from != to) {
				String item = adapterSchedule.getItem(from);
				adapterSchedule.remove(item);
				adapterSchedule.insert(item, to);
				inflateLayoutsSchedule();
			}
		}
	};

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.add_task);
		comment_pref = getSharedPreferences("Comment", 0);
		dialog = new ProgressDialog(this, ProgressDialog.THEME_HOLO_DARK);
		dialog.setMessage("Saving...");
		dialog.setCancelable(true);
		Density();
		btn = (Button) findViewById(R.id.add_task_main);
		pref = this.getSharedPreferences("Location", 0);
		label = this.getSharedPreferences("Lable", 0);
		attach = this.getSharedPreferences("Attach", 0);

		dialoglayout5 = getLayoutInflater().inflate(R.layout.add_location,
				null, false);

		db_initialize();
		aq = new AQuery(this);
		init();
	}

	@SuppressWarnings("deprecation")
	private void init() {

		aq.id(R.id.back).clicked(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (Constants.Project_task_check == 1) {
					getSupportFragmentManager().popBackStack();
				} else {
					finish();
				}
			}
		});

		dayPosition = getIntent().getExtras().getInt("position");

		// Initialize the ViewPager and set an adapter
		ViewPager pager = (ViewPager) aq.id(R.id.add_task_pager).getView();

		pager.setAdapter(new AddTaskPagerFragment(getSupportFragmentManager()));
		PageIndicator mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
		mIndicator.setViewPager(pager);
		mIndicator.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				Position = position;
				if (position == 0) {
					aq.id(R.id.page_title_header).text("Task");
					btn.setAlpha((float) 0.3);
					comment_pref.edit().clear().commit();
				}

				else if (position == 1) {
					aq.id(R.id.page_title_header).text("Event");
					btn.setAlpha((float) 0.3);
					comment_pref.edit().clear().commit();
				}

				else if (position == 2) {
					aq.id(R.id.page_title_header).text("Schedule");
					btn.setAlpha((float) 0.3);
					comment_pref.edit().clear().commit();

				} else if (position == 3) {
					aq.id(R.id.page_title_header).text("Appoinment");
					btn.setAlpha((float) 0.3);
					comment_pref.edit().clear().commit();
				}

				else if (position == 4) {
					aq.id(R.id.page_title_header).text("Project");
					btn.setAlpha((float) 0.3);

					comment_pref.edit().clear().commit();
				}

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int position) {
				// Position=position;

			}
		});

		layout_MainMenu = (FrameLayout) findViewById(R.id.main_container);
		layout_MainMenu.getForeground().setAlpha(0);

		final LayoutInflater inflater = (LayoutInflater) AddTask.this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View view = inflater.inflate(R.layout.popup_menu, null, false);
		TextView cancel = (TextView) view.findViewById(R.id.cancel);
		TextView ok = (TextView) view.findViewById(R.id.ok);

		final LayoutInflater inflater2 = (LayoutInflater) AddTask.this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View view2 = inflater2.inflate(R.layout.popup_menu_event, null,
				false);
		TextView cancel_event = (TextView) view2
				.findViewById(R.id.cancel_event);
		TextView ok_event = (TextView) view2.findViewById(R.id.ok_event);

		final View viewS = inflater2.inflate(R.layout.popup_menu_schedule,
				null, false);
		TextView cancel_schedule = (TextView) viewS
				.findViewById(R.id.cancel_event);
		TextView ok_schedule = (TextView) viewS.findViewById(R.id.ok_event);

		TextView tx = (TextView) view.findViewById(R.id.show_hid_text);
		tx.setTypeface(TypeFaces.get(this, Constants.MED_TYPEFACE));

		ArrayList<String> arrayList = new ArrayList<String>(
				Arrays.asList(array));

		ArrayList<String> arrayListEvent = new ArrayList<String>(
				Arrays.asList(array));

		ArrayList<String> arrayListSchedule = new ArrayList<String>(
				Arrays.asList(array));

		listViewSchedule = (DragSortListView) viewS
				.findViewById(R.id.list_schedule);
		adapterSchedule = new ArrayAdapter<String>(AddTask.this,
				R.layout.popup_menu_items_events, R.id.text, arrayListSchedule);

		listViewSchedule.setAdapter(adapterSchedule);
		listViewSchedule.setDropListener(onDropSchedule);

		for (int i = 0; i < array.length; i++)
			listViewSchedule.setItemChecked(i, true);

		listViewEvent = (DragSortListView) view2.findViewById(R.id.list_event);
		adapterEvent = new ArrayAdapter<String>(AddTask.this,
				R.layout.popup_menu_items_schedule, R.id.text, arrayListEvent);

		listViewEvent.setAdapter(adapterEvent);
		listViewEvent.setDropListener(onDropEvent);
		for (int i = 0; i < array.length; i++)
			listViewEvent.setItemChecked(i, true);

		listViewTask = (DragSortListView) view.findViewById(R.id.list);
		adapterTask = new ArrayAdapter<String>(AddTask.this,
				R.layout.popup_menu_items, R.id.text, arrayList);

		listViewTask.setAdapter(adapterTask);
		listViewTask.setDropListener(onDropTask);
		for (int i = 0; i < array.length; i++)
			listViewTask.setItemChecked(i, true);

		final View viewadd = getLayoutInflater().inflate(R.layout.add_menu,
				null, false);
		aq_menu = new AQuery(this, viewadd);
		popupWindowAdd = new PopupWindow(viewadd, Utils.getDpValue(200, this),
				WindowManager.LayoutParams.WRAP_CONTENT, true);

		popupWindowAdd.setBackgroundDrawable(getResources().getDrawable(
				android.R.drawable.dialog_holo_light_frame));
		popupWindowAdd.setOutsideTouchable(true);
		popupWindowAdd.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				aq.id(R.id.addtask_menu).image(
						getResources().getDrawable(R.drawable.ic_show_white));
			}
		});
		aq.id(R.id.addtask_menu).clicked(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (Position == 0) {
					if (AddTaskFragment.taskTitle.length() > 0) {
						aq_menu.id(R.id.menu_item1)
								.textColorId(R.color._4d4d4d);
						aq_menu.id(R.id.menu_item2)
								.textColorId(R.color._4d4d4d);
					}
				} else if (Position == 1) {
					if (AddEventFragment.taskTitle.length() > 0) {
						aq_menu.id(R.id.menu_item1)
								.textColorId(R.color._4d4d4d);
						aq_menu.id(R.id.menu_item2)
								.textColorId(R.color._4d4d4d);
					}
				}else if (Position == 2) {
					if (AddScheduleFragment.taskTitle.length() > 0) {
						aq_menu.id(R.id.menu_item1)
								.textColorId(R.color._4d4d4d);
						aq_menu.id(R.id.menu_item2)
								.textColorId(R.color._4d4d4d);
					}
				}else if (Position == 3) {
					if (AddAppoinmentFragment.taskTitle.length() > 0) {
						aq_menu.id(R.id.menu_item1)
								.textColorId(R.color._4d4d4d);
						aq_menu.id(R.id.menu_item2)
								.textColorId(R.color._4d4d4d);
					}
				}else if (Position == 4) {
					if (AddProjectFragment.taskTitle.length() > 0) {
						aq_menu.id(R.id.menu_item1)
								.textColorId(R.color._4d4d4d);
						aq_menu.id(R.id.menu_item2)
								.textColorId(R.color._4d4d4d);
					}
				}
				aq.id(R.id.addtask_menu).image(
						getResources().getDrawable(R.drawable.ic_show_black));
				if (popupWindowAdd.isShowing())
					popupWindowAdd.dismiss();
				else {
					popupWindowAdd.showAsDropDown(aq.id(R.id.addtask_menu)
							.getView(), 0, 10);
				}
			}

		});

		aq_menu.id(R.id.menu_item1).clicked(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
			}
		});
		aq_menu.id(R.id.menu_item2).clicked(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			}
		});

		popupWindowSchedule = new PopupWindow(viewS,
				Utils.getDpValue(270, this),
				WindowManager.LayoutParams.WRAP_CONTENT, true);
		popupWindowSchedule.setBackgroundDrawable(new BitmapDrawable());
		popupWindowSchedule.setOutsideTouchable(true);
		popupWindowSchedule.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				layout_MainMenu.getForeground().setAlpha(0);
			}
		});

		popupWindowTask = new PopupWindow(view, Utils.getDpValue(270, this),
				WindowManager.LayoutParams.WRAP_CONTENT, true);
		popupWindowTask.setBackgroundDrawable(new BitmapDrawable());
		popupWindowTask.setOutsideTouchable(true);
		popupWindowTask.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				layout_MainMenu.getForeground().setAlpha(0);
			}
		});

		// *******************PoP CUSTOMIZATION CANCEL OK LISNER

		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				popupWindowTask.dismiss();
			}
		});
		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				popupWindowTask.dismiss();
			}
		});

		cancel_event.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				popupWindowEvent.dismiss();
			}
		});
		ok_event.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				popupWindowEvent.dismiss();
			}
		});
		cancel_schedule.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popupWindowSchedule.dismiss();

			}
		});
		ok_schedule.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popupWindowSchedule.dismiss();

			}
		});

		popupWindowEvent = new PopupWindow(view2, Utils.getDpValue(270, this),
				WindowManager.LayoutParams.WRAP_CONTENT, true);
		popupWindowEvent.setBackgroundDrawable(new BitmapDrawable());
		popupWindowEvent.setOutsideTouchable(true);
		popupWindowEvent.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				layout_MainMenu.getForeground().setAlpha(0);
			}
		});

		DragSortController controllerTask = new DragSortController(listViewTask);
		controllerTask.setDragHandleId(R.id.drag_handle);

		listViewTask.setOnTouchListener(controllerTask);
		listViewTask.setOnItemClickListener(new ListClickListenerTask());

		aq_menu.id(R.id.menu_item3).clicked(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Utils.hidKeyboard(AddTask.this);
				if (Position == 0) {
					if (popupWindowTask.isShowing())
						popupWindowTask.dismiss();
					else {
						popupWindowAdd.dismiss();
						layout_MainMenu.getForeground().setAlpha(150);

						popupWindowTask.showAtLocation(
								aq.id(R.id.menu_dots_task).getView(),
								Gravity.CENTER_HORIZONTAL, 0, -3);
					}
				} else if (Position == 1) {

					if (popupWindowEvent.isShowing())
						popupWindowEvent.dismiss();
					else {
						popupWindowAdd.dismiss();
						layout_MainMenu.getForeground().setAlpha(150);

						popupWindowEvent.showAtLocation(
								aq.id(R.id.menu_dots_task).getView(),
								Gravity.CENTER_HORIZONTAL, 0, -7);
					}

				} else if (Position == 2) {

					if (popupWindowSchedule.isShowing())
						popupWindowSchedule.dismiss();
					else {
						popupWindowAdd.dismiss();
						layout_MainMenu.getForeground().setAlpha(150);

						popupWindowSchedule.showAtLocation(
								aq.id(R.id.menu_dots_task).getView(),
								Gravity.CENTER_HORIZONTAL, 0, -7);
					}

				}
			}
		});

		aq.id(R.id.menu_dots_task).clicked(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

		DragSortController controllerSchedule = new DragSortController(
				listViewSchedule);
		controllerSchedule.setDragHandleId(R.id.drag_handle);

		listViewSchedule.setOnTouchListener(controllerSchedule);
		listViewSchedule
				.setOnItemClickListener(new ListClickListenerSchedule());

		DragSortController controllerEvent = new DragSortController(
				listViewEvent);
		controllerEvent.setDragHandleId(R.id.drag_handle);

		listViewEvent.setOnTouchListener(controllerEvent);
		listViewEvent.setOnItemClickListener(new ListClickListenerEvent());

		aq.id(R.id.add_task_main).clicked(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				tod = tododao.loadAll();
				AddData();
			}
		});

		// DONOT Show location at the moment
		aq.id(R.layout.add_task_location1).gone();

		/*
		 * AddTask.aq_menu.id(R.id.menu_item2).clicked(new OnClickListener() {
		 * 
		 * @Override public void onClick(View arg0) { // TODO Auto-generated
		 * m111111ethod stub
		 * 
		 * 
		 * title = aq.id(R.id.event_title).getText() .toString();
		 * AddTask.popupWindowAdd.dismiss(); Fragment fr = new AddTaskComment();
		 * FragmentManager manager = getSupportFragmentManager();
		 * FragmentTransaction transaction = manager .beginTransaction(); if
		 * (Constants.Project_task_check == 1) {
		 * 
		 * transaction.replace(R.id.content_task, fr); } else {
		 * 
		 * transaction.replace(R.id.main_container, fr); }
		 * transaction.setCustomAnimations(R.anim.slide_in1, R.anim.slide_out1);
		 * transaction.addToBackStack(null); transaction.commit();
		 * 
		 * } });
		 */

		aq_menu.id(R.id.menu_item2).clicked(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated m111111ethod stub
				String title = null;
				if (Position == 0) {
					if (AddTaskFragment.taskTitle.length() > 0) {
						title = AddTaskFragment.taskTitle.getText().toString();
						aq_menu.id(R.id.menu_item1)
								.textColorId(R.color._4d4d4d);
						aq_menu.id(R.id.menu_item2)
								.textColorId(R.color._4d4d4d);
					}
				} else if (Position == 1) {
					if (AddEventFragment.taskTitle.length() > 0) {
						title = AddEventFragment.taskTitle.getText().toString();
						aq_menu.id(R.id.menu_item1)
								.textColorId(R.color._4d4d4d);
						aq_menu.id(R.id.menu_item2)
								.textColorId(R.color._4d4d4d);
					}
				} else if (Position == 2) {
					if (AddScheduleFragment.taskTitle.length() > 0) {
						title = AddScheduleFragment.taskTitle.getText()
								.toString();
						aq_menu.id(R.id.menu_item1)
								.textColorId(R.color._4d4d4d);
						aq_menu.id(R.id.menu_item2)
								.textColorId(R.color._4d4d4d);
					}
				}else if (Position == 3) {
					if (AddAppoinmentFragment.taskTitle.length() > 0) {
						title = AddAppoinmentFragment.taskTitle.getText()
								.toString();
						aq_menu.id(R.id.menu_item1)
								.textColorId(R.color._4d4d4d);
						aq_menu.id(R.id.menu_item2)
								.textColorId(R.color._4d4d4d);
					}
				}else if (Position == 4) {
					if (AddProjectFragment.taskTitle.length() > 0) {
						title = AddProjectFragment.taskTitle.getText()
								.toString();
						aq_menu.id(R.id.menu_item1)
								.textColorId(R.color._4d4d4d);
						aq_menu.id(R.id.menu_item2)
								.textColorId(R.color._4d4d4d);
					}
				}

				if (title != null && title.length() > 0) {
					popupWindowAdd.dismiss();
					Fragment fr = new AddTaskComment();
					FragmentManager manager = getSupportFragmentManager();
					FragmentTransaction transaction = manager
							.beginTransaction();
					if (Constants.Project_task_check == 1
							) {

						transaction.replace(R.id.content_task, fr);
					} else {

						transaction.replace(R.id.main_container, fr);
					}
					transaction.setCustomAnimations(R.anim.slide_in1,
							R.anim.slide_out1);
					transaction.addToBackStack(null);
					transaction.commit();

				}
			}
		});

	}

	public void inflateLayoutsTasks() {
		GridLayout gridLayout = (GridLayout) findViewById(R.id.inner_container);
		for (int key : AddTaskFragment.inflatingLayouts.keySet()) {
			GridLayout.LayoutParams param = new GridLayout.LayoutParams();
			param.height = LayoutParams.WRAP_CONTENT;
			param.width = LayoutParams.MATCH_PARENT;
			param.rowSpec = GridLayout.spec(key);
			View child = gridLayout
					.findViewById(AddTaskFragment.inflatingLayouts.get(key));
			child.setLayoutParams(param);
		}
		gridLayout.invalidate();
	}

	public void inflateLayoutsEvents() {
		GridLayout gridLayout = (GridLayout) findViewById(R.id.inner_event_container);
		for (int key : AddEventFragment.inflatingLayoutsEvents.keySet()) {
			GridLayout.LayoutParams param = new GridLayout.LayoutParams();
			param.height = LayoutParams.WRAP_CONTENT;
			param.width = LayoutParams.MATCH_PARENT;
			param.rowSpec = GridLayout.spec(key);
			View child = gridLayout
					.findViewById(AddEventFragment.inflatingLayoutsEvents
							.get(key));
			child.setLayoutParams(param);
		}
		gridLayout.invalidate();
	}

	public void inflateLayoutsSchedule() {
		GridLayout gridLayout = (GridLayout) findViewById(R.id.inner_container_scheduale);
		for (int key : AddScheduleFragment.inflatingLayouts.keySet()) {
			GridLayout.LayoutParams param = new GridLayout.LayoutParams();
			param.height = LayoutParams.WRAP_CONTENT;
			param.width = LayoutParams.MATCH_PARENT;
			param.rowSpec = GridLayout.spec(key);
			View child = gridLayout
					.findViewById(AddScheduleFragment.inflatingLayouts.get(key));
			child.setLayoutParams(param);
		}
		gridLayout.invalidate();
	}

	public class PageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int position) {
			if (position == 0) {
				Position = position;
				aq.id(R.id.menu_dots_task).visible();

			} else if (position == 1) {
				aq.id(R.id.page_title_header).text("Event");
				Position = position;
				aq.id(R.id.menu_dots_task).gone();
			}

		}

	}

	public class ListClickListenerTask implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			position = position + 1;
			if (position == 2) {
				position = position + 1;
			} else if (position > 1) {
				position = position + 1;
			}

			int layoutId = AddTaskFragment.inflatingLayouts.get(position);

			CheckedTextView checkedTextView = (CheckedTextView) view
					.findViewById(R.id.checkbox);
			if (checkedTextView.isChecked()) {
				aq.id(layoutId).visible();
			} else {
				aq.id(layoutId).gone();
			}

		}

	}

	public class ListClickListenerEvent implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			position = position + 1;
			if (position == 2) {
				position = position + 1;
			} else if (position > 1) {
				position = position + 1;
			}
			int layoutId = AddEventFragment.inflatingLayoutsEvents
					.get(position);
			CheckedTextView checkedTextView = (CheckedTextView) view
					.findViewById(R.id.checkbox);
			if (checkedTextView.isChecked()) {
				aq.id(layoutId).visible();
			} else {
				aq.id(layoutId).gone();
			}
		}

	}

	public class ListClickListenerSchedule implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			position = position + 1;
			if (position == 2) {
				position = position + 1;
			} else if (position > 1) {
				position = position + 1;
			}
			int layoutId = AddScheduleFragment.inflatingLayouts.get(position);
			CheckedTextView checkedTextView = (CheckedTextView) view
					.findViewById(R.id.checkbox);
			if (checkedTextView.isChecked()) {
				aq.id(layoutId).visible();
			} else {
				aq.id(layoutId).gone();
			}
		}

	}

	public class AddTaskPagerFragment extends FragmentPagerAdapter {

		public AddTaskPagerFragment(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return 5; // just Add Task & Add Event
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return "Add Task";
			case 1:
				return "Add Event";
			case 2:
				return "Schedule";
			case 3:
				return "Appointments";
			case 4:
				return "Project";
			default:
				return "";// not the case

			}
		}

		@Override
		public Fragment getItem(int position) {
			if (position == 0)
				return AddTaskFragment.newInstance(position, dayPosition);
			else if (position == 1)
				return AddEventFragment.newInstance(position, dayPosition);
			else if (position == 2)
				return AddScheduleFragment.newInstance(position, dayPosition);
			else if (position == 3)
				return AddAppoinmentFragment.newInstance(position, dayPosition);
			else if (position == 4)
				return AddProjectFragment.newInstance(position, dayPosition);
			else
				return null;
		}
	}

	public void AddData() {

		if (Position == 0) {
			if (!(aq.id(R.id.task_title1).getText().toString().equals(""))) {

				MaxId = attach.getInt("Max", 0);
				titlecheck = 1;

				title = aq.id(R.id.task_title1).getText().toString();

				is_allday = false;
				start_date = AddTaskFragment.currentYear + "-"
						+ (AddTaskFragment.currentMonDigit + 1) + "-"
						+ AddTaskFragment.currentDayDigit + " "
						+ AddTaskFragment.currentHours + ":"
						+ AddTaskFragment.currentMin + ":00";
				end_date = null;

				location = aq.id(R.id.location_task).getText().toString();

				before = aq.id(R.id.before).getText().toString();
				if (before.contains("On Arrive") || before.contains("On Leave")) {
					is_time = false;
					is_location = true;
					r_location = aq.id(R.id.location_before).getText()
							.toString();
					location_tag = ((TextView) AddTaskBeforeFragment.viewP)
							.getText().toString() + "";
				}

				if (!(aq.id(R.id.before).getText().toString().equals("") || aq
						.id(R.id.before).getText().toString() == null)) {
					is_alertEmail = aq.id(R.id.email_radio).getCheckBox()
							.isChecked();
					is_alertNotification = aq.id(R.id.notification_radio)
							.getCheckBox().isChecked();
				}

				repeat_forever = aq.id(R.id.forever_radio).isChecked();
				repeat = aq.id(R.id.repeat).getText().toString();
				repeatdate = AddTaskFragment.repeatdate;

				label_name = aq.id(R.id.spinner_labels_task).getText()
						.toString();

				toggleCheckList(aq.id(R.id.add_sub_task).getView());
				checklist_data = aq.id(R.id.add_sub_task).getEditText()
						.getText().toString();

				notes = aq.id(R.id.notes_task).getText().toString();

			}
		} else if (Position == 1) {
			if (!(aq.id(R.id.event_title).getText().toString().equals(""))) {

				MaxId = attach.getInt("2Max", 0);
				titlecheck = 2;

				title = aq.id(R.id.event_title).getText().toString();

				ToggleButton switCh = (ToggleButton) findViewById(R.id.switch_event);
				is_allday = switCh.isChecked();

				start_date = AddEventFragment.currentYear + "-"
						+ (AddEventFragment.currentMonDigit + 1) + "-"
						+ AddEventFragment.currentDayDigit + " "
						+ AddEventFragment.currentHours + ":"
						+ AddEventFragment.currentMin + ":00";
				end_date = null;

				location = aq.id(R.id.location_event).getText().toString();

				before = aq.id(R.id.before_event).getText().toString();
				if (before.contains("On Arrive") || before.contains("On Leave")) {
					is_time = false;
					is_location = true;
					r_location = aq.id(R.id.location_before_event).getText()
							.toString();

					location_tag = ((TextView) AddEventBeforeFragment.viewP)
							.getText().toString() + "";
				}

				if (!(aq.id(R.id.before_event).getText().toString().equals("") || aq
						.id(R.id.before_event).getText().toString() == null)) {
					is_alertEmail = aq.id(R.id.email_radio_event).getCheckBox()
							.isChecked();
					is_alertNotification = aq.id(R.id.notification_radio_event)
							.getCheckBox().isChecked();
				}

				repeat_forever = aq.id(R.id.repeat_forever_radio).isChecked();
				repeat = aq.id(R.id.repeat_event).getText().toString();
				repeatdate = AddEventFragment.repeatdate;

				label_name = aq.id(R.id.spinner_labels_event).getText()
						.toString();

				toggleCheckList(aq.id(R.id.add_sub_event).getView());
				checklist_data = aq.id(R.id.add_sub_event).getEditText()
						.getText().toString();

				notes = aq.id(R.id.notes_event).getText().toString();

			}
		} else if (Position == 2) {
			if (!(aq.id(R.id.sch_title).getText().toString().equals(""))) {

				MaxId = attach.getInt("3Max", 0);
				titlecheck = 3;

				title = aq.id(R.id.sch_title).getText().toString();

				ToggleButton switCh = (ToggleButton) findViewById(R.id.switch_sch);

				is_allday = switCh.isChecked();

				start_date = AddScheduleFragment.currentYear + "-"
						+ (AddScheduleFragment.currentMonDigit + 1) + "-"
						+ AddScheduleFragment.currentDayDigit + " "
						+ AddScheduleFragment.currentHours + ":"
						+ AddScheduleFragment.currentMin + ":00";
				end_date = null;

				location = aq.id(R.id.sch_location).getText().toString();

				before = aq.id(R.id.before_schedule).getText().toString();
				if (before.contains("On Arrive") || before.contains("On Leave")) {
					is_time = false;
					is_location = true;
					r_location = aq.id(R.id.location_before_sch).getText()
							.toString();

					location_tag = ((TextView) AddScheduleBeforeFragment.viewP)
							.getText().toString() + "";
				}

				if (!(aq.id(R.id.before_schedule).getText().toString()
						.equals("") || aq.id(R.id.before_schedule).getText()
						.toString() == null)) {
					is_alertEmail = aq.id(R.id.email_radio_sch).getCheckBox()
							.isChecked();
					is_alertNotification = aq.id(R.id.notification_radio_sch)
							.getCheckBox().isChecked();
				}

				repeat_forever = aq.id(R.id.sch_forever_radio).isChecked();
				repeat = aq.id(R.id.sch_repeat_txt).getText().toString();
				repeatdate = AddScheduleFragment.repeatdate;

				label_name = aq.id(R.id.sch_label_txt).getText().toString();

				toggleCheckList(aq.id(R.id.add_sub_sch).getView());
				checklist_data = aq.id(R.id.add_sub_sch).getEditText()
						.getText().toString();

				notes = aq.id(R.id.notes_schedule).getText().toString();

			}
		} else if (Position == 3) {
			if (!(aq.id(R.id.appoinment_title).getText().toString().equals(""))) {

				MaxId = attach.getInt("4Max", 0);
				titlecheck = 4;

				title = aq.id(R.id.appoinment_title).getText().toString();

				is_allday = false;

				start_date = AddAppoinmentFragment.currentYear + "-"
						+ (AddAppoinmentFragment.currentMonDigit + 1) + "-"
						+ AddAppoinmentFragment.currentDayDigit + " "
						+ AddAppoinmentFragment.currentHours + ":"
						+ AddAppoinmentFragment.currentMin + ":00";
				end_date = null;

				location = null;

				before = aq.id(R.id.before_appoinment).getText().toString();
				if (before.contains("On Arrive") || before.contains("On Leave")) {
					is_time = false;
					is_location = true;
					r_location = aq.id(R.id.location_before_appoin).getText()
							.toString();

					location_tag = ((TextView) AddAppoinmentBeforeFragment.viewP)
							.getText().toString() + "";
				}

				if (!(aq.id(R.id.before_appoinment).getText().toString()
						.equals("") || aq.id(R.id.before_appoinment).getText()
						.toString() == null)) {
					is_alertEmail = aq.id(R.id.email_radio_appoin)
							.getCheckBox().isChecked();
					is_alertNotification = aq
							.id(R.id.notification_radio_appoin).getCheckBox()
							.isChecked();
				}
				repeat = null;

				repeatdate = null;

				label_name = aq.id(R.id.spinner_labels_appoin).getText()
						.toString();

				toggleCheckList(aq.id(R.id.add_sub_appoinment).getView());
				checklist_data = aq.id(R.id.add_sub_appoinment).getEditText()
						.getText().toString();

				notes = aq.id(R.id.notes_appoinment).getText().toString();

			}
		} else if (Position == 4) {
			if (!(aq.id(R.id.project_title).getText().toString().equals(""))) {

				MaxId = attach.getInt("5Max", 0);
				titlecheck = 5;

				title = aq.id(R.id.project_title).getText().toString();

				is_allday = false;

				start_date = AddProjectFragment.currentYear + "-"
						+ (AddProjectFragment.currentMonDigit + 1) + "-"
						+ AddProjectFragment.currentDayDigit + " "
						+ AddProjectFragment.currentHours + ":"
						+ AddProjectFragment.currentMin + ":00";
				end_date = null;

				location = null;

				before = null;
				is_time = false;
				is_location = false;
				r_location = null;
				location_tag = "";
				// }

				is_alertEmail = false;
				is_alertNotification = false;

				repeat = null;

				repeatdate = null;

				label_name = aq.id(R.id.spinner_labels_project).getText()
						.toString();

				checklist_data = null;

				notes = null;

			}
		}
		if (tod.size() > 0)
			todo_id = tod.get(tod.size() - 1).getId() + 1;

		if (titlecheck != -1) {

			givenDateString = AddTaskFragment.currentYear + "-"
					+ (AddTaskFragment.currentMonDigit + 1) + "-"
					+ AddTaskFragment.currentDayDigit;
			sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy hh:mm");
			String givenDateString1 = AddTaskFragment.currentDayDigit + "-"
					+ (AddTaskFragment.currentMonDigit + 1) + "-"
					+ AddTaskFragment.currentYear + " "
					+ AddTaskFragment.currentHours + ":"
					+ AddTaskFragment.currentMin;

			try {
				mDate = sdf1.parse(givenDateString1);
				dateInMilliseconds = mDate.getTime();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if (before != null) {
				if (is_time == true) {
					if (before.contains(" Mins") || before.contains(" Min"))
						r_time = (Integer.parseInt(before
								.replaceAll("\\D+", "")) * 60 * 1000);
					else if (before.contains(" Hour")
							|| before.contains(" Hours"))
						r_time = (Integer.parseInt(before
								.replaceAll("\\D+", "")) * 60 * 60 * 1000);
					else if (before.contains(" Day")
							|| before.contains(" Days"))
						r_time = (Integer.parseInt(before
								.replaceAll("\\D+", "")) * 24 * 60 * 60 * 1000);
					else if (before.contains(" Week")
							|| before.contains(" Weeks"))
						r_time = (Integer.parseInt(before
								.replaceAll("\\D+", "")) * 7 * 24 * 60 * 60 * 1000);
					else if (before.contains(" Month")
							|| before.contains(" Months"))
						r_time = (Integer.parseInt(before
								.replaceAll("\\D+", "")) * 31 * 24 * 60 * 60 * 1000);
					else if (before.contains(" Year")
							|| before.contains(" Years"))
						r_time = (Integer.parseInt(before
								.replaceAll("\\D+", ""))
								* 12
								* 31
								* 24
								* 60
								* 60 * 1000);
				} else {
					if (before.contains("On Arrive")) {
						is_locationtype = 0;
						locationtype = "On Arrive";
					} else if (before.contains("On Leave")) {
						is_locationtype = 1;
						locationtype = "On Leave";
					}
				}
			}
			if (repeat != null) {
				if (repeat.contains("once") || repeat.contains("Once")) {
					r_repeat = 0;
					repeat = "once";
				} else if (repeat.contains("daily") || repeat.contains("daily")) {
					r_repeat = 24 * 60 * 60 * 1000;
					repeat = "daily";
				} else if (repeat.contains("weekly")
						|| repeat.contains("Weekly")) {
					r_repeat = 7 * 24 * 60 * 60 * 1000;
					repeat = "weekly";
				} else if (repeat.contains("monthly")
						|| repeat.contains("Monthly")) {
					r_repeat = 31 * 24 * 60 * 60 * 1000;
					repeat = "monthly";
				} else if (repeat.contains("yearly")
						|| repeat.contains("Yearly")) {
					r_repeat = 12 * 31 * 24 * 60 * 60 * 1000;
					repeat = "yearly";
				}
			}
			AlarmManagerBroadcastReceiver alarm = new AlarmManagerBroadcastReceiver();

			ToDo todoset = new ToDo();
			todoset.setUser_id(Constants.user_id);
			todoset.setTodo_type_id(titlecheck);
			todoset.setTitle(title);
			todoset.setStart_date(dateInMilliseconds);
			todoset.setEnd_date(dateInMilliseconds);
			todoset.setIs_allday(is_allday);
			todoset.setLocation(location);
			todoset.setNotes(notes);

			Label label = new Label();
			label.setLabel_name(label_name);
			label.setId(f2);
			labeldao.insert(label);
			todoset.setLabel(label);

			Reminder reminderr = new Reminder();
			reminderr.setId(f2);
			reminderr.setIs_alertEmail(is_alertEmail);
			reminderr.setIs_alertNotification(is_alertNotification);
			reminderr.setIs_time_location(is_location);
			reminderr.setLocation(r_location);
			reminderr.setLocation_type(is_locationtype);
			if ((!location_tag.equals("New")) && location_tag != null) {
				reminderr.setLocation_tag(location_tag);
			}

			reminderr.setTime(r_time);
			reminderdao.insert(reminderr);
			todoset.setReminder(reminderr);

			Repeat repeaT = new Repeat();
			repeaT.setId(f2);
			repeaT.setRepeat_interval(repeat);
			repeaT.setRepeat_until(r_repeat);
			repeaT.setIs_forever(repeat_forever);
			repeatdao.insert(repeaT);
			todoset.setRepeat(repeaT);

			CheckList checklist = new CheckList();
			checklist.setId(f2);
			checklist.setTitle(checklist_data);
			checklistdao.insert(checklist);
			todoset.setCheckList(checklist);

			for (int i = 1; i <= MaxId; i++) {
				pairs.add(new BasicNameValuePair("todo_attachment[" + (i - 1)
						+ "][attachment_path]", AddTask.attach.getString(
						titlecheck + "path" + i, null)));
			}

			MaxId = 0;

			if (comment != null && comment.size() > 0) {
				for (int i = 0; i < comment.size(); i++) {

					Comment commenT = new Comment();
					commenT.setId(f2);
					commenT.setComment(comment.get(i));
					commenT.setToDo(todoset);
					commentdao.insert(commenT);

				}
			}

			Friends friend = new Friends();
			friend.setId(f2);
			friend.setEmail("email");
			friendsdao.insert(friend);
			todoset.setReminder(reminderr);
			tododao.insert(todoset);

			TaskListFragment.todayQuery();
			TaskListFragment.todayAdapter.notifyDataSetChanged();

			alarm.SetcustAlarm(this);

			// ********************* Data add hit Asyntask
			asyn = new add();
			asyn.execute();
			Toast.makeText(AddTask.this, "Added", Toast.LENGTH_SHORT).show();

		} else
			Toast.makeText(AddTask.this, "Please Enter Title",
					Toast.LENGTH_SHORT).show();

	}

	public void db_initialize() {
		checklistdao = App.daoSession.getCheckListDao();
		friendsdao = App.daoSession.getFriendsDao();
		labeldao = App.daoSession.getLabelDao();
		sharedao = App.daoSession.getShareDao();
		tododao = App.daoSession.getToDoDao();
		commentdao = App.daoSession.getCommentDao();
		repeatdao = App.daoSession.getRepeatDao();
		reminderdao = App.daoSession.getReminderDao();
	}

	public void Density() {
		DisplayMetrics metrics1 = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics1);
		switch (metrics1.densityDpi) {
		case DisplayMetrics.DENSITY_LOW:
			Constants.density = 120f;
			break;
		case DisplayMetrics.DENSITY_MEDIUM:
			Constants.density = 160f;
			break;
		case DisplayMetrics.DENSITY_HIGH:
			Constants.density = 240f;
			break;
		case DisplayMetrics.DENSITY_XHIGH:
			Constants.density = 320f;
			break;
		case DisplayMetrics.DENSITY_XXHIGH:
			Constants.density = 480f;
			break;
		case DisplayMetrics.DENSITY_XXXHIGH:
			Constants.density = 640f;
			break;
		}
	}

	public class add extends AsyncTask<String, Integer, Void> {

		@Override
		protected Void doInBackground(String... params) {

			try {
				post.setEntity(new UrlEncodedFormEntity(pairs));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}

			try {
				response = client.execute(post);
			} catch (ClientProtocolException e1) {
				e1.printStackTrace();
				asyn.cancel(true);
			} catch (IOException e1) {
				e1.printStackTrace();
				asyn.cancel(true);
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			String temp = null;
			dialog.dismiss();
			try {
				temp = EntityUtils.toString(response.getEntity());
				Log.e("Task Added?", temp);
			} catch (org.apache.http.ParseException | IOException e) {
				e.printStackTrace();
				asyn.cancel(true);
			}

			finish();

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog.show();
			client = new DefaultHttpClient();
			post = new HttpPost("http://api.heuristix.net/one_todo/v1/task/add");
			pairs = new ArrayList<NameValuePair>();

			pairs.add(new BasicNameValuePair("todo[user_id]", Constants.user_id
					+ ""));

			if (titlecheck != -1)
				pairs.add(new BasicNameValuePair("todo[todo_type_id]",
						titlecheck + ""));

			pairs.add(new BasicNameValuePair("todo[title]", title));

			if (start_date != null)
				pairs.add(new BasicNameValuePair("todo[start_date]", start_date
						+ ""));

			if (end_date != null) {
				pairs.add(new BasicNameValuePair("todo[end_date]",
						"2014-11-02 04:05:05"));
			}

			if (notes != null)
				pairs.add(new BasicNameValuePair("todo[notes]", notes));

			if (before != null) {
				if (is_location == false) {
					pairs.add(new BasicNameValuePair("todo_reminder[time]",
							r_time + ""));
				} else {
					pairs.add(new BasicNameValuePair("todo_reminder[location]",
							r_location));

					if (!location_tag.equals("New")) {
						pairs.add(new BasicNameValuePair(
								"todo_reminder[location_tag]", location_tag));
					}
					pairs.add(new BasicNameValuePair(
							"todo_reminder[location_type]", locationtype));
				}
			}
			if (repeat != null) {
				pairs.add(new BasicNameValuePair(
						"todo_repeat[repeat_interval]", repeat));

				if (repeat_forever != true)
					pairs.add(new BasicNameValuePair(
							"todo_repeat[repeat_until]", repeatdate));
			}
			for (int i = 1; i <= MaxId; i++) {
				pairs.add(new BasicNameValuePair("todo_attachment[" + (i - 1)
						+ "][attachment_path]", AddTask.attach.getString(
						titlecheck + "path" + i, null)));
			}

			MaxId = 0;
			if (comment != null && comment.size() > 0) {
				for (int i = 0; i < comment.size(); i++) {
					pairs.add(new BasicNameValuePair("todo_comment[" + (i - 1)
							+ "][user_id]", Constants.user_id + ""));
					pairs.add(new BasicNameValuePair("todo_comment[" + (i - 1)
							+ "][comment]", comment.get(i)));
					pairs.add(new BasicNameValuePair("todo_comment[" + (i - 1)
							+ "][date_time]", commenttime.get(i)));
				}
			}

			if (checklist_data != null)
				pairs.add(new BasicNameValuePair(
						"todo_checklist[checklist_data]", checklist_data));
			
			if(!assignedSelectedName.isEmpty()){
				Toast.makeText(getApplicationContext(), assignedSelectedID, Toast.LENGTH_SHORT).show();
				pairs.add(new BasicNameValuePair(
						"todo_collaborate[assignee_id]", assignedSelectedID));
				pairs.add(new BasicNameValuePair(
						"todo_collaborate[invitee_id]", String.valueOf(App.prefs.getUserId())));
			}

		}

	}

	private void toggleCheckList(View switchView) {
		View newView;

		/*
		 * Here is where the job is done. By simply calling an instance of the
		 * ChecklistManager we can call its methods.
		 */
		try {
			// Getting instance
			ChecklistManager mChecklistManager = ChecklistManager
					.getInstance(this);

			/*
			 * These method are useful when converting from EditText to
			 * ChecklistView (but can be set anytime, they'll be used at
			 * appropriate moment)
			 */

			// Setting new entries hint text (if not set no hint
			// will be used)
			mChecklistManager.setNewEntryHint("Add a subtask");
			// Let checked items are moved on bottom

			mChecklistManager.setMoveCheckedOnBottom(1);

			mChecklistManager
					.setCheckListChangedListener(new CheckListChangedListener() {

						@Override
						public void onCheckListChanged() {

						}
					});

			// Decide if keep or remove checked items when converting
			// back to simple text from checklist
			mChecklistManager.setKeepChecked(true);

			// I want to make checks symbols visible when converting
			// back to simple text from checklist
			mChecklistManager.setShowChecks(true);

			// Converting actual EditText into a View that can
			// replace the source or viceversa
			newView = mChecklistManager.convert(switchView);

			// Replacing view in the layout
			mChecklistManager.replaceViews(switchView, newView);

			// Updating the instance of the pointed view for
			// eventual reverse conversion
			switchView = newView;

		} catch (ViewNotSupportedException e) {
			// This exception is fired if the source view class is
			// not supported
			e.printStackTrace();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		comment_pref.edit().clear().commit();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if( ! assignedSelectedName.isEmpty()){
			aq.id(R.id.task_assign).text(assignedSelectedName);
		}
	}
	
	

}
