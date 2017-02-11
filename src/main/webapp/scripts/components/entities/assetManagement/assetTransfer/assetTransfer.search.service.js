'use strict';

angular.module('stepApp')
    .factory('AssetTransferSearch', function ($resource) {
        return $resource('api/_search/assetTransfers/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
