'use strict';

angular.module('stepApp')
    .factory('HrSpouseInfo', function ($resource, DateUtils) {
        return $resource('api/hrSpouseInfos/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.birthDate = DateUtils.convertLocaleDateFromServer(data.birthDate);
                    data.actionDate = DateUtils.convertLocaleDateFromServer(data.actionDate);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.birthDate = DateUtils.convertLocaleDateToServer(data.birthDate);
                    data.actionDate = DateUtils.convertLocaleDateToServer(data.actionDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.birthDate = DateUtils.convertLocaleDateToServer(data.birthDate);
                    data.actionDate = DateUtils.convertLocaleDateToServer(data.actionDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    }).factory('HrSpouseInfoApprover', function ($resource) {
        return $resource('api/hrSpouseInfosApprover/:id', {},
            {
                'update': { method: 'POST'},
                'query': { method: 'GET', isArray: true}
            });
    }).factory('HrSpouseInfoApproverLog', function ($resource) {
        return $resource('api/hrSpouseInfosApprover/log/:entityId', {},
            {
                'update': { method: 'POST'},
                'query': { method: 'GET', isArray: true}
            });
    });
