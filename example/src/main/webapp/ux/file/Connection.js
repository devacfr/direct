(function() {

	Ext.define('Ext.ux.file.Connection', {
		extend : 'Ext.data.Connection',
		requires : 'Ext.data.Connection',
		constructor : function() {

			this.addEvents('uploading', 'downloading');
			this.callParent(arguments);
		},

		request : function(options) {
			var me = this, origXhr = this.getXhrInstance;

			if (this.hasListener('uploading')) {
				this.getXhrInstance = function() {
					var xhr = origXhr.apply(me);

					if (xhr.upload) {
						xhr.upload.addEventListener('progress', function(evt) {
							me.fireEvent('progress', {
								amt : evt.loaded,
								total : evt.lengthComputable ? evt.total : null,
								evt : evt
							});
						}, false);
					}
					return xhr;
				};
			}
			if (this.hasListener('downloading')) {
				this.getXhrInstance = function() {
					var xhr = origXhr.apply(me);

					if (xhr.onprogress) {
						xhr.addEventListener('progress', function(evt) {
							me.fireEvent('progress', {
								dir : 'down',
								amt : evt.loaded,
								total : evt.lengthComputable ? evt.total : null,
								evt : evt
							});
						}, false);
					}

					return xhr;
				};
			}

			this.callParent(arguments);
		}

	});
})();
