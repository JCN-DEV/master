'use strict';

describe('AclObjectIdentity Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockAclObjectIdentity, MockAclClass, MockAclSid;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockAclObjectIdentity = jasmine.createSpy('MockAclObjectIdentity');
        MockAclClass = jasmine.createSpy('MockAclClass');
        MockAclSid = jasmine.createSpy('MockAclSid');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'AclObjectIdentity': MockAclObjectIdentity,
            'AclClass': MockAclClass,
            'AclSid': MockAclSid
        };
        createController = function() {
            $injector.get('$controller')("AclObjectIdentityDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:aclObjectIdentityUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
