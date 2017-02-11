'use strict';

angular.module('stepApp')
    .factory('AssetAuctionInformationSearch', function ($resource) {
        return $resource('api/_search/assetAuctionInformations/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
