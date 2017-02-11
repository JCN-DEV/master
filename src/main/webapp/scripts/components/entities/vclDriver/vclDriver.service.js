'use strict';

angular.module('stepApp')
    .factory('VclDriver', function ($resource, DateUtils) {
        return $resource('api/vclDrivers/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.joinDate = DateUtils.convertLocaleDateFromServer(data.joinDate);
                    data.retirementDate = DateUtils.convertLocaleDateFromServer(data.retirementDate);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.joinDate = DateUtils.convertLocaleDateToServer(data.joinDate);
                    data.retirementDate = DateUtils.convertLocaleDateToServer(data.retirementDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.joinDate = DateUtils.convertLocaleDateToServer(data.joinDate);
                    data.retirementDate = DateUtils.convertLocaleDateToServer(data.retirementDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    });
