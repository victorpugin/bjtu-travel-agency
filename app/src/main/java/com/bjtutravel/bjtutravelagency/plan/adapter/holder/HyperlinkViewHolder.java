package com.bjtutravel.bjtutravelagency.plan.adapter.holder;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjtutravel.bjtutravelagency.R;
import com.bjtutravel.bjtutravelagency.models.ItemPlan;
import com.bjtutravel.bjtutravelagency.plan.detail.DetailPlanActivity;
import com.bjtutravel.bjtutravelagency.web.WebActivity;
import com.leocardz.link.preview.library.LinkPreviewCallback;
import com.leocardz.link.preview.library.SourceContent;
import com.leocardz.link.preview.library.TextCrawler;

public class HyperlinkViewHolder extends BaseViewHolder implements View.OnClickListener {
    private static final String TAG = "PlanRecyclerViewAdapter";

    private final Context mContext;

    private final TextCrawler mTextCrawler;
    private final LinkPreviewCallback mLinkPreviewCallback;

    public final LinearLayout previewLayout;
    public final TextView title;
    public final TextView url;
    public final TextView content;

    private String mFinalUrl;

    public HyperlinkViewHolder(View itemView, final Context context) {
        super(itemView);

        mContext = context;

        mTextCrawler = new TextCrawler();
        mLinkPreviewCallback = new LinkPreviewCallback() {
            @Override
            public void onPre() {
                previewLayout.setVisibility(View.GONE);
            }

            @Override
            public void onPos(final SourceContent sourceContent, boolean isNull) {
                if (isNull ||sourceContent.getFinalUrl().equals("")) {
                    Log.e(TAG, "Failed to load preview url");
                } else {
                    mFinalUrl = sourceContent.getFinalUrl();
                    title.setText(sourceContent.getTitle());
                    url.setText(sourceContent.getCannonicalUrl());
                    content.setText(sourceContent.getDescription());

                    previewLayout.setVisibility(View.VISIBLE);
                    previewLayout.setOnClickListener(HyperlinkViewHolder.this);
                }
            }
        };

        previewLayout = (LinearLayout) itemView.findViewById(R.id.preview_layout);
        title = (TextView) itemView.findViewById(R.id.title);
        url = (TextView) itemView.findViewById(R.id.url);
        content = (TextView) itemView.findViewById(R.id.content);
    }

    @Override
    public void onBind(ItemPlan itemPlan) {
        mTextCrawler.makePreview(mLinkPreviewCallback, itemPlan.getContent());
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(mContext, WebActivity.class);
        intent.putExtra("url", mFinalUrl);
        mContext.startActivity(intent);
    }
}
