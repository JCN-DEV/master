'use strict';

angular.module('stepApp')
    .factory('HrWingHeadSetup', function ($resource, DateUtils) {
        return $resource('api/hrWingHeadSetups/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.joinDate = DateUtils.convertLocaleDateFromServer(data.joinDate);
                    data.endDate = DateUtils.convertLocaleDateFromServer(data.endDate);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.joinDate = DateUtils.convertLocaleDateToServer(data.joinDate);
                    data.endDate = DateUtils.convertLocaleDateToServer(data.endDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.joinDate = DateUtils.convertLocaleDateToServer(data.joinDate);
                    data.endDate = DateUtils.convertLocaleDateToServer(data.endDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    })
    .factory('HrWingHeadSetupByWing', function ($resource)
    {
        return $resource('api/hrWingHeadSetupsByWing/:wingId', {}, {
            'get': { method: 'GET', isArray: true}
        });
    });
