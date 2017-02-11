'use strict';

angular.module('stepApp')
    .factory('AssetRequisitionSearch', function ($resource) {
        return $resource('api/_search/assetRequisitions/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
