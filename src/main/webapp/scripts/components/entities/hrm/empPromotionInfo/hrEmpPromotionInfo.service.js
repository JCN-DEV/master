'use strict';

angular.module('stepApp')
    .factory('HrEmpPromotionInfo', function ($resource, DateUtils) {
        return $resource('api/hrEmpPromotionInfos/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.joiningDate = DateUtils.convertLocaleDateFromServer(data.joiningDate);
                    data.orderDate = DateUtils.convertLocaleDateFromServer(data.orderDate);
                    data.goDate = DateUtils.convertLocaleDateFromServer(data.goDate);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.joiningDate = DateUtils.convertLocaleDateToServer(data.joiningDate);
                    data.orderDate = DateUtils.convertLocaleDateToServer(data.orderDate);
                    data.goDate = DateUtils.convertLocaleDateToServer(data.goDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.joiningDate = DateUtils.convertLocaleDateToServer(data.joiningDate);
                    data.orderDate = DateUtils.convertLocaleDateToServer(data.orderDate);
                    data.goDate = DateUtils.convertLocaleDateToServer(data.goDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    }).factory('HrEmpPromotionInfoApprover', function ($resource) {
        return $resource('api/hrEmpPromotionInfosApprover/:id', {},
            {
                'update': { method: 'POST'},
                'query': { method: 'GET', isArray: true}
            });
    }).factory('HrEmpPromotionInfoApproverLog', function ($resource) {
        return $resource('api/hrEmpPromotionInfosApprover/log/:entityId', {},
            {
                'update': { method: 'POST'},
                'query': { method: 'GET', isArray: true}
            });
    });
