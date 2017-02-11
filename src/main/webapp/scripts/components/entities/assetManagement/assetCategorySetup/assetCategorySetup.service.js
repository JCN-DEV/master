'use strict';

angular.module('stepApp')
    .factory('AssetCategorySetup', function ($resource, DateUtils) {
        return $resource('api/assetCategorySetups/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {

                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    })
    .factory('AssetCategorySetupByTypeId', function ($resource, DateUtils) {
            return $resource('api/assetCategorySetups/getByType/:id', {}, {
                'query': { method: 'GET', isArray: true}
            });
        })

    ;
