'use strict';

angular.module('stepApp')
    .factory('AlmAttendanceInformation', function ($resource, DateUtils) {
        return $resource('api/almAttendanceInformations/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.officeIn = DateUtils.convertLocaleDateFromServer(data.officeIn);
                    data.officeOut = DateUtils.convertLocaleDateFromServer(data.officeOut);
                    data.punchIn = DateUtils.convertLocaleDateFromServer(data.punchIn);
                    data.punchOut = DateUtils.convertLocaleDateFromServer(data.punchOut);
                    data.processDate = DateUtils.convertLocaleDateFromServer(data.processDate);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.officeIn = DateUtils.convertLocaleDateToServer(data.officeIn);
                    data.officeOut = DateUtils.convertLocaleDateToServer(data.officeOut);
                    data.punchIn = DateUtils.convertLocaleDateToServer(data.punchIn);
                    data.punchOut = DateUtils.convertLocaleDateToServer(data.punchOut);
                    data.processDate = DateUtils.convertLocaleDateToServer(data.processDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.officeIn = DateUtils.convertLocaleDateToServer(data.officeIn);
                    data.officeOut = DateUtils.convertLocaleDateToServer(data.officeOut);
                    data.punchIn = DateUtils.convertLocaleDateToServer(data.punchIn);
                    data.punchOut = DateUtils.convertLocaleDateToServer(data.punchOut);
                    data.processDate = DateUtils.convertLocaleDateToServer(data.processDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    });
