Ext.require([ 'Ext.window.Window', 'Ext.chart.*' ]);

Ext
		.onReady(function() {
			var chart, timeAxis;
			Ext.direct.Manager.addProvider(Ext.app.REMOTING_API);
			var data = [];
			var group = false, groupOp = [ {
				dateFormat : 'M d',
				groupBy : 'year,month,day'
			}, {
				dateFormat : 'M',
				groupBy : 'year,month'
			} ];

			function regroup() {
				group = !group;
				var axis = chart.axes.get(1), selectedGroup = groupOp[+group];
				axis.dateFormat = selectedGroup.dateFormat;
				axis.groupBy = selectedGroup.groupBy;

				chart.redraw();
			}

			var store = Ext.create('Ext.data.JsonStore', {
				fields : [ 'date', 'visits', 'views', 'veins' ],
				data : []
			}), startDate = new Date(2011, 0, 1);

			Ext.direct.Manager
					.addProvider(
							Ext.app.REMOTING_API,
							{
								interval : 1000,
								type : 'polling',
								url : Ext.app.POLLING_URLS.liveUpdate,
								baseParams : {
									startDate : startDate
								},
								listeners : {
									data : function(provider, event) {
										provider.baseParams = {};
										var val = event.data;
										var toDate = timeAxis.toDate, lastDate = new Date(
												val.date), markerIndex = chart.markerIndex || 0;
										if (+toDate < +lastDate) {
											markerIndex = 1;
											timeAxis.toDate = lastDate;
											timeAxis.fromDate = Ext.Date
													.add(
															Ext.Date
																	.clone(timeAxis.fromDate),
															Ext.Date.DAY, 1);
											chart.markerIndex = markerIndex;
										}

										data.push(val);
										store.loadData(data);
									}
								}
							});

			var win = Ext.create('Ext.window.Window', {
				width : 800,
				height : 600,
				minHeight : 400,
				minWidth : 550,
				maximizable : true,
				title : 'Live Updated Chart',
				layout : 'fit',
				items : [ {
					xtype : 'chart',
					style : 'background:#fff',
					store : store,
					itemId : 'chartCmp',
					axes : [ {
						type : 'Numeric',
						minimum : 0,
						maximum : 100,
						position : 'left',
						fields : [ 'views', 'visits', 'veins' ],
						title : 'Number of Hits',
						grid : {
							odd : {
								fill : '#dedede',
								stroke : '#ddd',
								'stroke-width' : 0.5
							}
						}
					}, {
						type : 'Time',
						position : 'bottom',
						fields : 'date',
						title : 'Day',
						dateFormat : 'M d',
						groupBy : 'year,month,day',
						aggregateOp : 'sum',

						constrain : true,
						fromDate : startDate,
						toDate : new Date(2011, 0, 7)
					} ],
					series : [ {
						type : 'line',
						axis : [ 'left', 'bottom' ],
						xField : 'date',
						yField : 'visits',
						label : {
							display : 'none',
							field : 'visits',
							renderer : function(v) {
								return v >> 0;
							},
							'text-anchor' : 'middle'
						},
						markerConfig : {
							radius : 5,
							size : 5
						}
					}, {
						type : 'line',
						axis : [ 'left', 'bottom' ],
						xField : 'date',
						yField : 'views',
						label : {
							display : 'none',
							field : 'visits',
							renderer : function(v) {
								return v >> 0;
							},
							'text-anchor' : 'middle'
						},
						markerConfig : {
							radius : 5,
							size : 5
						}
					}, {
						type : 'line',
						axis : [ 'left', 'bottom' ],
						xField : 'date',
						yField : 'veins',
						label : {
							display : 'none',
							field : 'visits',
							renderer : function(v) {
								return v >> 0;
							},
							'text-anchor' : 'middle'
						},
						markerConfig : {
							radius : 5,
							size : 5
						}
					} ]
				} ]
			}).show();
			chart = win.child('#chartCmp');
			timeAxis = chart.axes.get(1);
		});
