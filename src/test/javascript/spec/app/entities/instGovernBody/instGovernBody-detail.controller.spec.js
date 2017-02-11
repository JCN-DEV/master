'use strict';

describe('InstGovernBody Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockInstGovernBody, MockInstitute;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockInstGovernBody = jasmine.createSpy('MockInstGovernBody');
        MockInstitute = jasmine.createSpy('MockInstitute');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'InstGovernBody': MockInstGovernBody,
            'Institute': MockInstitute
        };
        createController = function() {
            $injector.get('$controller')("InstGovernBodyDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:instGovernBodyUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
