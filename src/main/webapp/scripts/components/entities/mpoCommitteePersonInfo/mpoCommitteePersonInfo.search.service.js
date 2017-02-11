'use strict';

angular.module('stepApp')
    .factory('MpoCommitteePersonInfoSearch', function ($resource) {
        return $resource('api/_search/mpoCommitteePersonInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('MpoCommitteeOneByEmail', function ($resource) {
        return $resource('api/mpoCommitteePersonInfos/email/:email', {}, {
            'get': { method: 'GET', isArray: false}
        });
    });
