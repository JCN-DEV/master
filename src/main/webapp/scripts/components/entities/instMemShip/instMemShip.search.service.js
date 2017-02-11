'use strict';

angular.module('stepApp')
    .factory('InstMemShipSearch', function ($resource) {
        return $resource('api/_search/instMemShips/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('CurrentInstCommitteeMembers', function ($resource) {
        return $resource('api/instMemShips/institute/current', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('CurrentInstCommitteeMemberByEmail', function ($resource) {
        return $resource('api/instMemShips/institute/current/findMember/:email', {}, {
            'query': { method: 'GET', isArray: false}
        });
    });
