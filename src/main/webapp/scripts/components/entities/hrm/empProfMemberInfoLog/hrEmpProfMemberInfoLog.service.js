'use strict';

angular.module('stepApp')
    .factory('HrEmpProfMemberInfoLog', function ($resource, DateUtils) {
        return $resource('api/hrEmpProfMemberInfoLogs/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.membershipDate = DateUtils.convertLocaleDateFromServer(data.membershipDate);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.actionDate = DateUtils.convertLocaleDateFromServer(data.actionDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.membershipDate = DateUtils.convertLocaleDateToServer(data.membershipDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.actionDate = DateUtils.convertLocaleDateToServer(data.actionDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.membershipDate = DateUtils.convertLocaleDateToServer(data.membershipDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.actionDate = DateUtils.convertLocaleDateToServer(data.actionDate);
                    return angular.toJson(data);
                }
            }
        });
    });
