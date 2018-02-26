using QlikView.Qvx.QvxLibrary;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Webpals.Qvx.QvxConnector1
{
    /// <summary>
    /// Test connection
    /// </summary>
    internal class QvTestConnection : QvxConnection
    {
        public override void Init()
        {
            QvxLog.SetLogLevels(true, true);
            QvxLog.Log(QvxLogFacility.Application, QvxLogSeverity.Notice, "Init()");

            var fields = BuildFields();

            MTables = new List<QvxTable>
            {
                new QvxTable
                {
                    TableName="TestTable",
                    GetRows = GetTestRows,
                    Fields = fields
                }
            };
        }

        private IEnumerable<QvxDataRow> GetTestRows()
        {
            QvxLog.Log(QvxLogFacility.Application, QvxLogSeverity.Debug, "GetTestRows");
            for(int i = 1; i <= 3; ++i)
            {
                yield return MakeEntry(i, FindTable("TestTable", MTables));
            }
        }

        private QvxDataRow MakeEntry(int i, QvxTable table)
        {
            var row = new QvxDataRow();
            row[table.Fields[0]] = $"f1 {i}";
            row[table.Fields[1]] = i;

            return row;
        }

        private QvxField[] BuildFields()
        {
            return new QvxField[]
            {
                new QvxField("Field1", QvxFieldType.QVX_TEXT, QvxNullRepresentation.QVX_NULL_FLAG_SUPPRESS_DATA, FieldAttrType.ASCII),
                new QvxField("Field2", QvxFieldType.QVX_SIGNED_INTEGER, QvxNullRepresentation.QVX_NULL_FLAG_SUPPRESS_DATA, FieldAttrType.INTEGER)
            };
        }
    }
}
