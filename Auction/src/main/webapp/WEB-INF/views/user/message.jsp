<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../../template/header.jsp"%>

<link href="styles/fade-tab-nav.css" rel="stylesheet">

<div class="container">
  <div class="row">
    <div class="col-lg-12">
      <div class="title">
        <h2>
          <span>消息中心</span>
        </h2>
      </div>
    </div>
  </div>
  <div class="row">
    <div class="col-md-10 col-md-offset-1 col-sm-10 col-sm-offset-1 col-xs-10 col-xs-offset-1">
      <ul class="nav nav-pills nav-justified">
        <li class="active"><a href="#tab_a" data-toggle="pill">未读消息</a></li>
        <li><a href="#tab_b" data-toggle="pill">已读消息</a></li>
      </ul>
      <br />
      <div class="tab-content">
        <div id="tab_a" class="tab-pane fade in active">
          <c:choose>
            <c:when test="${unreadBidNotifications == null || fn:length(unreadBidNotifications) == 0 }">
              <label>暂无未读的交易成功通知</label>
            </c:when>
            <c:otherwise>
              <div id="unreadAccordion" role="tablist" aria-multiselectable="true">
              <c:forEach var="unreadBidNotification" items="${unreadBidNotifications }">
                <div class="panel panel-default">
                  <div class="panel-heading" role="tab" id="unreadHeading${unreadBidNotification.id }">
                    <h4 class="panel-title">
                      <a data-toggle="collapse" data-parent="#unreadAccordion" href="#unreadCollapse${unreadBidNotification.id }" aria-expanded="false" aria-controls="#unreadCollapse${unreadBidNotification.id }">
                        <span>您成功拍得 ${unreadBidNotification.bid.product.name }！点我查看详情！${unreadBidNotification.bid.dealDate }</span>
                      </a>
                    </h4>
                  </div>
                  <div id="unreadCollapse${unreadBidNotification.id }" class="panel-collapse collapse" role="tabpanel" aria-labelledby="unreadHeading${unreadBidNotification.id }">
                    <div class="row">
                      <div class="col-lg-10 col-lg-offset-1">
                        <span>系统提示：</span>
                        <span>亲爱的${sessionScope.loginuser.userName }：</span>
                        <p>您好！恭喜您以
                        <span class="label label-danger">￥${unreadBidNotification.bid.price }</span>的价格拍下
                        <span class="label label-danger">${unreadBidNotification.bid.product.name }</span>，成交日期为：${unreadBidNotification.bid.dealDate }。请您及时联系
                        <span class="label label-danger">${unreadBidNotification.bid.product.user.userName }（${unreadBidNotification.bid.product.user.email }）</span>进行线下交易。
                        </p>
                        <span><a href="message/markread/${unreadBidNotification.id }">标为已读</a></span>
                      </div>
                    </div>
                  </div>
                </div>
              </c:forEach>
              </div>
            </c:otherwise>
          </c:choose>
        </div>
        <div id="tab_b" class="tab-pane fade">
          <c:choose>
            <c:when test="${historyBidNotifications == null || fn:length(historyBidNotifications) == 0}">
              <label>暂无历史通知信息！</label>
            </c:when>
            <c:otherwise>
              <div id="historyAccordion" role="tablist" aria-multiselectable="true">
              <c:forEach var="historyBidNotification" items="${historyBidNotifications }">
                <div class="panel panel-default">
                  <div class="panel-heading" role="tab" id="historyHeading${historyBidNotification.id }">
                    <h4 class="panel-title">
                      <a data-toggle="collapse" data-parent="#historyAccordion" href="#historyCollapse${historyBidNotification.id }" aria-expanded="false" aria-controls="historyCollapse${historyBidNotification.id }">
                        <span>您成功拍得 ${historyBidNotification.bid.product.name }！点我查看详情！${historyBidNotification.bid.dealDate }</span>
                      </a>
                    </h4>
                  </div>
                  <div id="historyCollapse${historyBidNotification.id }" class="panel-collapse collapse" role="tabpanel" aria-labelledby="historyHeading${historyBidNotification.id }">
                    <div class="row">
                      <div class="col-lg-10 col-lg-offset-1">
                        <span>系统提示：</span>
                        <span>亲爱的${sessionScope.loginuser.userName }：</span>
                        <p>您好！恭喜您以
                        <span class="label label-danger">￥${historyBidNotification.bid.price }</span>的价格拍下
                        <span class="label label-danger">${historyBidNotification.bid.product.name }</span>，成交日期为：${historyBidNotification.bid.dealDate }。
                        </p>
                      </div>
                    </div>
                  </div>
                </div>
              </c:forEach>
              </div>
            </c:otherwise>
          </c:choose>
        </div>
      </div>
    </div>
  </div>
</div>

<%@ include file="../../../template/footer.jsp"%>