'use strict';

angular.module('stepApp')
    .factory('HrEmpTransferInfo', function ($resource, DateUtils) {
        return $resource('api/hrEmpTransferInfos/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.fromDate = DateUtils.convertLocaleDateFromServer(data.fromDate);
                    data.toDate = DateUtils.convertLocaleDateFromServer(data.toDate);
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
                    data.goDate = DateUtils.convertLocaleDateToServer(data.goDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    }).factory('HrEmpTransferInfoApprover', function ($resource) {
        return $resource('api/hrEmpTransferInfosApprover/:id', {},
            {
                'update': { method: 'POST'},
                'query': { method: 'GET', isArray: true}
            });
    }).factory('HrEmpTransferInfoApproverLog', function ($resource) {
        return $resource('api/hrEmpTransferInfosApprover/log/:entityId', {},
            {
                'update': { method: 'POST'},
                'query': { method: 'GET', isArray: true}
            });
    });
