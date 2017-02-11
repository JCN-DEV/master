'use strict';

angular.module('stepApp')
    .factory('AssetDestroySearch', function ($resource) {
        return $resource('api/_search/assetDestroys/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
