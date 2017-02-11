'use strict';

angular.module('stepApp')
    .factory('HrEmpChildrenInfo', function ($resource, DateUtils) {
        return $resource('api/hrEmpChildrenInfos/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.dateOfBirth = DateUtils.convertLocaleDateFromServer(data.dateOfBirth);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.dateOfBirth = DateUtils.convertLocaleDateToServer(data.dateOfBirth);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.dateOfBirth = DateUtils.convertLocaleDateToServer(data.dateOfBirth);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    }).factory('HrEmpChildrenInfoApprover', function ($resource) {
        return $resource('api/hrEmpChildrenInfosApprover/:id', {},
            {
                'update': { method: 'POST'},
                'query': { method: 'GET', isArray: true}
            });
    }).factory('HrEmpChildrenInfoApproverLog', function ($resource) {
        return $resource('api/hrEmpChildrenInfosApprover/log/:entityId', {},
            {
                'update': { method: 'POST'},
                'query': { method: 'GET', isArray: true}
            });
    });
