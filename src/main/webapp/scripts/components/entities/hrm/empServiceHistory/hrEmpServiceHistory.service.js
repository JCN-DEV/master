'use strict';

angular.module('stepApp')
    .factory('HrEmpServiceHistory', function ($resource, DateUtils) {
        return $resource('api/hrEmpServiceHistorys/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.serviceDate = DateUtils.convertLocaleDateFromServer(data.serviceDate);
                    data.gazettedDate = DateUtils.convertLocaleDateFromServer(data.gazettedDate);
                    data.encadrementDate = DateUtils.convertLocaleDateFromServer(data.encadrementDate);
                    data.goDate = DateUtils.convertLocaleDateFromServer(data.goDate);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.serviceDate = DateUtils.convertLocaleDateToServer(data.serviceDate);
                    data.gazettedDate = DateUtils.convertLocaleDateToServer(data.gazettedDate);
                    data.encadrementDate = DateUtils.convertLocaleDateToServer(data.encadrementDate);
                    data.goDate = DateUtils.convertLocaleDateToServer(data.goDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.serviceDate = DateUtils.convertLocaleDateToServer(data.serviceDate);
                    data.gazettedDate = DateUtils.convertLocaleDateToServer(data.gazettedDate);
                    data.encadrementDate = DateUtils.convertLocaleDateToServer(data.encadrementDate);
                    data.goDate = DateUtils.convertLocaleDateToServer(data.goDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    }).factory('HrEmpServiceHistoryApprover', function ($resource) {
        return $resource('api/hrEmpServiceHistorysApprover/:id', {},
            {
                'update': { method: 'POST'},
                'query': { method: 'GET', isArray: true}
            });
    }).factory('HrEmpServiceHistoryApproverLog', function ($resource) {
        return $resource('api/hrEmpServiceHistorysApprover/log/:entityId', {},
            {
                'update': { method: 'POST'},
                'query': { method: 'GET', isArray: true}
            });
    }).factory('HrEmpServiceHistoryByEmployee', function ($resource) {
        return $resource('api/hrEmpServiceHistorysByEmp/:empId', {},
            {
                'query': { method: 'GET', isArray: true}
            });
    });
