'use strict';

angular.module('stepApp')
    .factory('AssetTypeSetup', function ($resource, DateUtils) {
        return $resource('api/assetTypeSetups/:id', {}, {
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
    .factory('AssetTypeSetupByTypeCode',function($resource){
        return $resource('api/assetTypeset/assetTypesetTypeCodeUnique/:typeCode',{},{
        'query': {method: 'GET', isArray: true}
        })
        })
        .factory('AssetTypeSetupByTypeName',function($resource){
        return $resource('api/assetTypeset/assetTypesetTypeNameUnique/:typeName',{},{
        'query': {method: 'GET', isArray: true}
        })
        });
