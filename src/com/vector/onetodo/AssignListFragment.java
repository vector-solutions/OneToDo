package com.vector.onetodo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vector.onetodo.db.gen.Assign;
import com.vector.onetodo.db.gen.AssignDao;
import com.vector.onetodo.utils.Utils;

public class AssignListFragment extends ProjectsTabHolder implements
		OnScrollListener {

	private ListView listView;
	private int position;
	private static long[] Currentdate;
	private RelativeLayout last;
	private AssignDao dao;
	private List<Assign> contactsList = new ArrayList<Assign>();
	private ImageView img;

	public static AssignListFragment newInstance(int position) {
		AssignListFragment myFragment = new AssignListFragment();
		Bundle args = new Bundle();
		args.putInt("position", position);
		myFragment.setArguments(args);
		return myFragment;
	}

	@Override
	public void onResume() {
		super.onResume();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.invitation_list, container, false);
		listView = (ListView) view.findViewById(R.id.invitation_list_view);
		img = (ImageView) getActivity().findViewById(R.id.assign_add);
		Currentdate = new long[3];
		String date_string = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i <= 2; i++) {
			date_string = Utils.getCurrentYear(i) + "-"
					+ (Utils.getCurrentMonthDigit(i) + 1) + "-"
					+ Utils.getCurrentDayDigit(i);
			try {
				Date mDate = sdf.parse(date_string);
				Currentdate[i] = mDate.getTime();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		position = getArguments().getInt("position");
		setadapter(getActivity(), position);
		dao = App.daoSession.getAssignDao();
		contactsList = dao.loadAll();
		listView.setOnScrollListener(this);
		listView.setAdapter(new ContactsAdapter(getActivity()));

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View container,
					int arg2, long arg3) {
				LinearLayout linearLayoutParent = (LinearLayout) container;
				RelativeLayout linearLayoutChild = (RelativeLayout) linearLayoutParent
						.getChildAt(1);
				if (last != null) {
					ImageView tvCount1 = (ImageView) last.getChildAt(2);
					tvCount1.setVisibility(View.GONE);
				}
				last = linearLayoutChild;
				ImageView tvCountry = (ImageView) linearLayoutChild
						.getChildAt(2);
				tvCountry.setVisibility(View.VISIBLE);
				img.setAlpha((float) 1);
				AddTask.assignedSelectedID = String.valueOf(contactsList.get(position).getFriends_id());
				AddTask.assignedSelectedName = contactsList.get(position).getName();
			}
		});

		img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (img.getAlpha() == 1) {
					getActivity().getSupportFragmentManager().popBackStack();
				}
			}
		});

	}

	public class ContactsAdapter extends BaseAdapter {

		Context context;

		public ContactsAdapter(Context context) {
			this.context = context;
		}

		@Override
		public int getCount() {
			return contactsList.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int ID) {
			return ID;
		}

		@Override
		public View getView(int position, View view1, ViewGroup parent) {
			View view = view1;
			Holder holder = null;
			if (view == null) {
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(R.layout.add_task_assign_item, parent,
						false);
				holder = new Holder();
				holder.title = (TextView) view.findViewById(R.id.assign_name);
				holder.number = (TextView) view
						.findViewById(R.id.assign_contact);
				view.setTag(holder);
			} else {
				holder = (Holder) view.getTag();
			}
			holder.title.setText(contactsList.get(position).getName());
			// holder.title.setText(contactsList.get(position).get);
			return view;
		}
	}

	class Holder {
		TextView title, number, time, icon;
	}

	@Override
	public void adjustScroll(int scrollHeight) {
		if (scrollHeight == 0 && listView.getFirstVisiblePosition() >= 1) {
			return;
		}

		listView.setSelectionFromTop(1, scrollHeight);
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if (mScrollTabHolder != null)
			mScrollTabHolder.onScroll(view, firstVisibleItem, visibleItemCount,
					totalItemCount, position);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// nothing
	}

	private void setadapter(Context context, int position) {
		ContactsAdapter adapter = new ContactsAdapter(getActivity());
		listView.setAdapter(adapter);
	}
}