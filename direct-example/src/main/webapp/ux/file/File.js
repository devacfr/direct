Ext.define('Ext.ux.file.File', {
	extend : 'Ext.data.Model',
	requires : 'Ext.data.Model',
	idProperty : 'id',
	constructor : function(data) {
		if (typeof data.raw == "undefined") {
			throw "Ext.ux.file.File: raw property missing. Can't create a file without raw data.";
		}
		var ns = Ext.ux.file.File;
		this.raw = data.raw;

		if (ns.isFile(this.raw)) {
			data.type = 'file';
			data.size = this.raw.size;
			data.name = this.raw.name;
		} else if (ns.isFileField(this.raw)) {
			data.type = 'form';
			data.size = -1;
			data.name = this.raw.getValue();
		} else {
			throw "Ext.ux.file.File: Unable to determine file type.";
		}

		this.callParent(arguments);
	},

	statics : {
		isFile : function(f) {
			return Ext.ux.file.FileManager.supportsFile && File.prototype.isPrototypeOf(f);
		},

		isFileField : function(f) {
			return Ext.ClassManager.getName(f) == "Ext.form.field.File";
		}
	},
	fields : [ {
		name : 'id',
		type : 'int'
	}, {
		name : 'name',
		type : 'string'
	}, {
		name : 'size',
		type : 'int'
	}, {
		name : 'type',
		type : 'string'
	} ],
	validations : [ {
		type : 'inclusion',
		field : 'type',
		list : [ 'form', 'file' ]
	} ],

	raw : null

});
