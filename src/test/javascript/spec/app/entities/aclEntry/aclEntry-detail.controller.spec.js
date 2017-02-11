'use strict';

describe('AclEntry Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockAclEntry, MockAclObjectIdentity, MockAclSid;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockAclEntry = jasmine.createSpy('MockAclEntry');
        MockAclObjectIdentity = jasmine.createSpy('MockAclObjectIdentity');
        MockAclSid = jasmine.createSpy('MockAclSid');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'AclEntry': MockAclEntry,
            'AclObjectIdentity': MockAclObjectIdentity,
            'AclSid': MockAclSid
        };
        createController = function() {
            $injector.get('$controller')("AclEntryDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:aclEntryUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
