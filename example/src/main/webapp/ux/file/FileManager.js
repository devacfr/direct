Ext.define('Ext.ux.file.FileManager', {
	mixins : [ 'Ext.util.Observable' ],
	requires : [ 'Ext.util.Observable', 'Ext.data.Store', 'Ext.ux.file.File' ],
	statics : {
		supportsFile : typeof window["File"] != "undefined"
	},
	constructor : function() {
		var me = this, fs = Ext.create('Ext.data.Store', {
			model : 'Ext.ux.file.File'
		});

		me.addEvents('fileadded', 'fileremoved');

		me.addFile = function(file) {
			var id, fileRec, afterCreate = function(rec) {
				id = fs.getCount() * -1;
				rec.setId(id);
				rec.phantom = false;
				rec.raw = file;
				fileRec = rec;
			};

			Ext.Array.each(fs.add({
				raw : file
			}), afterCreate);
			me.fireEvent('fileadded', fileRec);
			return fileRec;
		};

		me.removeFile = function(file) {
			var idx = fs.findBy(function(record) {
				return record.getId() === file.getId();
			});
			if (idx >= 0) {
				fs.removeAt(idx);
				me.fireEvent('fileremoved', file);
			}
		};

		me.each = function(fn, scope) {
			fs.each(function(record) {
				return fn.call(scope || me, record);
			});
		};
	}
});
