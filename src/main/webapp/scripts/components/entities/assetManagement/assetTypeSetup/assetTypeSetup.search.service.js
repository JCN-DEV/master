'use strict';

angular.module('stepApp')
    .factory('AssetTypeSetupSearch', function ($resource) {
        return $resource('api/_search/assetTypeSetups/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    })
    /*.factory('AssetTypeSetupByCode', function ($resource) {
        return $resource('api/assetTypeSetups/code/:code', {}, {
            'query': {method: 'GET', isArray: true}
        });
    })*/
    //.factory('AssetTypeSetupAll', function ($resource) {
    //    return $resource('api/assetTypeSetups', {}, {
    //        'query': {method: 'GET', isArray: true}
    //    });
    //})



;
