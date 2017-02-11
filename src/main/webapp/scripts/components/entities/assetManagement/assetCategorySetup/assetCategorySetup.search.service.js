'use strict';

angular.module('stepApp')
    .factory('AssetCategorySetupSearch', function ($resource) {
        return $resource('api/_search/assetCategorySetups/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
