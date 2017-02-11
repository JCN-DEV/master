'use strict';

angular.module('stepApp')
    .factory('AssetDistributionSearch', function ($resource) {
        return $resource('api/_search/assetDistributions/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
