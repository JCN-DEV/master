'use strict';

angular.module('stepApp')
    .factory('MiscConfigSetup', function ($resource, DateUtils) {
        return $resource('api/miscConfigSetups/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    })
    .factory('MiscConfigSetupByStat', function ($resource) {
        return $resource('api/miscConfigSetupsByStat/:stat', {},
            {
                'get': { method: 'GET', isArray: true}
            });
    })
    .factory('MiscConfigSetupByName', function ($resource) {
        return $resource('api/miscConfigSetupsByName/:name', {},
            {
                'get': { method: 'GET'}
            });
    });;
