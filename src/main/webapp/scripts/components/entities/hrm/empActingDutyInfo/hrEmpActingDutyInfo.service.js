'use strict';

angular.module('stepApp')
    .factory('HrEmpActingDutyInfo', function ($resource, DateUtils) {
        return $resource('api/hrEmpActingDutyInfos/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.fromDate = DateUtils.convertLocaleDateFromServer(data.fromDate);
                    data.toDate = DateUtils.convertLocaleDateFromServer(data.toDate);
                    data.officeOrderDate = DateUtils.convertLocaleDateFromServer(data.officeOrderDate);
                    data.goDate = DateUtils.convertLocaleDateFromServer(data.goDate);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.fromDate = DateUtils.convertLocaleDateToServer(data.fromDate);
                    data.toDate = DateUtils.convertLocaleDateToServer(data.toDate);
                    data.officeOrderDate = DateUtils.convertLocaleDateToServer(data.officeOrderDate);
                    data.goDate = DateUtils.convertLocaleDateToServer(data.goDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.fromDate = DateUtils.convertLocaleDateToServer(data.fromDate);
                    data.toDate = DateUtils.convertLocaleDateToServer(data.toDate);
                    data.officeOrderDate = DateUtils.convertLocaleDateToServer(data.officeOrderDate);
                    data.goDate = DateUtils.convertLocaleDateToServer(data.goDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    }).factory('HrEmpActingDutyInfoApprover', function ($resource) {
        return $resource('api/hrEmpActingDutyInfosApprover/:id', {},
            {
                'update': { method: 'POST'},
                'query': { method: 'GET', isArray: true}
            });
    }).factory('HrEmpActingDutyInfoApproverLog', function ($resource) {
        return $resource('api/hrEmpActingDutyInfosApprover/log/:entityId', {},
            {
                'update': { method: 'POST'},
                'query': { method: 'GET', isArray: true}
            });
    });
