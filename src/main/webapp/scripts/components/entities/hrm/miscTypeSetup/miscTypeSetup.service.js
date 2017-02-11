'use strict';

angular.module('stepApp')
    .factory('MiscTypeSetup', function ($resource, DateUtils) {
        return $resource('api/miscTypeSetups/:id', {}, {
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
    .factory('MiscTypeSetupByCategory', function ($resource) {
        return $resource('api/miscTypeSetupsByCat/:cat/:stat', {},
        {
            'get': { method: 'GET', isArray: true}
        });
    }).factory('MiscTypeSetupByCatNameOrTitle', function ($resource) {
        return $resource('api/miscTypeSetupsByCatNameTitle/:chktype/:cat/:value', {},
            {
                'get': { method: 'GET'}
            });
    });
