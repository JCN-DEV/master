'use strict';

describe('JpEmployeeReference Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockJpEmployeeReference, MockJpEmployee;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockJpEmployeeReference = jasmine.createSpy('MockJpEmployeeReference');
        MockJpEmployee = jasmine.createSpy('MockJpEmployee');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'JpEmployeeReference': MockJpEmployeeReference,
            'JpEmployee': MockJpEmployee
        };
        createController = function() {
            $injector.get('$controller')("JpEmployeeReferenceDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:jpEmployeeReferenceUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
