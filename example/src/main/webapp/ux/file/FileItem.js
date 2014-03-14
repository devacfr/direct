/**
 * A UI component represeting a single file that will be uploaded.
 */
Ext.define('Ext.ux.file.FileItem', {
	extend : 'Ext.container.Container',
	requires : [ 'Ext.container.Container', 'Ext.ProgressBar' ],
	alias : 'widget.fileitem',
	config : {

		itemTpl : Ext.core.DomHelper.createTemplate('{name} ({size} bytes)')
	},

	constructor : function(name, size, config) {
		var me = this, nameCmp = undefined;

		me.addEvents('remove');
		me.initConfig(config);

		me.getItemTpl().compile();
		me.name = name;
		me.size = size;

		me.setProgress = Ext.Function.createThrottled(function(amt) {
			me.nameCmp.setVisible(false);
			me.progress.setVisible(true);
			me.progress.updateProgress(amt / me.size);
		}, 100);

		me.callParent([ config ]);
	},

	setStatus : function(success) {
		var me = this, mkToolbar = function(item) {
			var arr = [ '->', {
				xtype : 'tool',
				type : 'close',
				listeners : {
					click : {
						fn : function() {
							Ext.defer(function() {
								me.ownerCt.remove(me);
							}, 100);
						},
						single : true
					}
				}
			} ];

			// insert the item given at the front
			// of our common toolbar
			Ext.Array.splice(arr, 0, 0, item);

			return {
				xtype : 'toolbar',
				height : "100%",
				items : arr
			};
		}, toolbar = me.down('toolbar');

		toolbar.setVisible(false);
		me.progress.reset();

		if (success) {
			me.add(mkToolbar({
				xtype : 'container',
				style : {
					color : 'green'
				},
				html : 'Uploaded ' + Ext.String.htmlEncode(me.name) + '.'
			}));
		} else {
			me.add(mkToolbar({
				xtype : 'container',
				style : {
					color : 'red'
				},
				html : 'Failed to upload ' + Ext.String.htmlEncode(me.name) + '.'
			}));
		}
	},
	initComponent : function() {
		var me = this;
		me.callParent();

		me.progress = Ext.create('Ext.ProgressBar', {
			flex : 1,
			hidden : true,
			text : me.getItemTpl().apply({
				name : me.name,
				size : me.size
			})
		});
		me.nameCmp = Ext.create('Ext.container.Container', {
			flex : 1,
			html : me.getItemTpl().apply({
				name : me.name,
				size : me.size
			})
		});

		me.add({
			xtype : 'toolbar',
			height : "100%",
			items : [ me.nameCmp, me.progress, '->', {
				xtype : 'tool',
				type : 'close',
				width : 25,
				listeners : {
					click : function() {
						me.fireEvent('remove');
					}
				}
			} ]
		});
	}
});
