'use strict';

angular.module('stepApp')
    .factory('AlmOnDutyLeaveApp', function ($resource, DateUtils) {
        return $resource('api/almOnDutyLeaveApps/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.applicationDate = DateUtils.convertLocaleDateFromServer(data.applicationDate);
                    data.dutyDate = DateUtils.convertLocaleDateFromServer(data.dutyDate);
                    data.endDutyDate = DateUtils.convertLocaleDateFromServer(data.endDutyDate);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.applicationDate = DateUtils.convertLocaleDateToServer(data.applicationDate);
                    data.dutyDate = DateUtils.convertLocaleDateToServer(data.dutyDate);
                    data.endDutyDate = DateUtils.convertLocaleDateToServer(data.endDutyDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.applicationDate = DateUtils.convertLocaleDateToServer(data.applicationDate);
                    data.dutyDate = DateUtils.convertLocaleDateToServer(data.dutyDate);
                    data.endDutyDate = DateUtils.convertLocaleDateToServer(data.endDutyDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    });
