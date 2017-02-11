'use strict';

angular.module('stepApp')
    .factory('AlmEmpLeaveApplication', function ($resource, DateUtils) {
        return $resource('api/almEmpLeaveApplications/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.applicationDate = DateUtils.convertLocaleDateFromServer(data.applicationDate);
                    data.leaveFromDate = DateUtils.convertLocaleDateFromServer(data.leaveFromDate);
                    data.leaveToDate = DateUtils.convertLocaleDateFromServer(data.leaveToDate);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.applicationDate = DateUtils.convertLocaleDateToServer(data.applicationDate);
                    data.leaveFromDate = DateUtils.convertLocaleDateToServer(data.leaveFromDate);
                    data.leaveToDate = DateUtils.convertLocaleDateToServer(data.leaveToDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.applicationDate = DateUtils.convertLocaleDateToServer(data.applicationDate);
                    data.leaveFromDate = DateUtils.convertLocaleDateToServer(data.leaveFromDate);
                    data.leaveToDate = DateUtils.convertLocaleDateToServer(data.leaveToDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    });
