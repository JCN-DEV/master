'use strict';

angular.module('stepApp')
    .factory('AssetSupplierSearch', function ($resource) {
        return $resource('api/_search/assetSuppliers/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
