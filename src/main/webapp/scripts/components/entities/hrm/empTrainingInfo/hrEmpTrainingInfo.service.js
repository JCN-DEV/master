'use strict';

angular.module('stepApp')
    .factory('HrEmpTrainingInfo', function ($resource, DateUtils) {
        return $resource('api/hrEmpTrainingInfos/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.durationFrom = DateUtils.convertLocaleDateFromServer(data.durationFrom);
                    data.durationTo = DateUtils.convertLocaleDateFromServer(data.durationTo);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.durationFrom = DateUtils.convertLocaleDateToServer(data.durationFrom);
                    data.durationTo = DateUtils.convertLocaleDateToServer(data.durationTo);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.durationFrom = DateUtils.convertLocaleDateToServer(data.durationFrom);
                    data.durationTo = DateUtils.convertLocaleDateToServer(data.durationTo);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    }).factory('HrEmpTrainingInfoApprover', function ($resource) {
        return $resource('api/hrEmpTrainingInfosApprover/:id', {},
            {
                'update': { method: 'POST'},
                'query': { method: 'GET', isArray: true}
            });
    }).factory('HrEmpTrainingInfoApproverLog', function ($resource) {
        return $resource('api/hrEmpTrainingInfosApprover/log/:entityId', {},
            {
                'update': { method: 'POST'},
                'query': { method: 'GET', isArray: true}
            });
    });
